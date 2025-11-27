package com.bongtu.baekseo.core.util

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Material3 기본 리플 모션 제거 함수
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

/**
 * combineClick Ripple 효과 제거 함수
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleCombineClickable(
    crossinline onClick: () -> Unit = {},
    crossinline onLongClick: () -> Unit,
): Modifier = composed {
    combinedClickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() },
        onLongClick = { onLongClick() },
    )
}

/**
 * Throttle 처리 함수
 */
inline fun Modifier.throttledNoRippleClickable(
    throttleTime: Long = 100L,
    coroutineScope: CoroutineScope,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    var isClickable by remember { mutableStateOf(true) }

    noRippleClickable {
        if (isClickable) {
            isClickable = false
            coroutineScope.launch {
                delay(throttleTime)
                onClick()
                isClickable = true
            }
        }
    }
}

/**
 * 화면의 빈 영역을 터치했을 때 포커스를 해제하는 함수
 * @param focusManager 현재 화면의 [FocusManager] 객체
 * @param doOnClear 포커스 해제 시 추가로 실행할 동작
 */
fun Modifier.clearFocus(
    focusManager: FocusManager,
    doOnClear: () -> Unit = {},
): Modifier = this.pointerInput(Unit) {
    detectTapGestures(onTap = {
        doOnClear()
        focusManager.clearFocus()
    })
}

/**
 * ScrollBar를 그리는 함수
 * @param scrollState 스크롤 상태 감지
 * @param showScrollBarTrack 스크롤바의 배경 표시 여부
 * @param thumbWidth thumb의 너비
 * @param trackPadding thumb과 track 사이의 간격
 * @param trackColor track 색상
 * @param thumbColor thumb 색상
 * @param trackCornerRadius track 모서리 둥글기 정도
 * @param thumbCornerRadius thumb 모서리 둥글기 정도
 * @param topPadding 스크롤바 전체 영역의 top 여백
 * @param bottomPadding 스크롤바 전체 영역의 bottom 여백
 * @param endPadding 스크롤바 전체 영역의 end 여백
 */
@Composable
fun Modifier.verticalScrollbar(
    scrollState: ScrollState,
    showScrollBarTrack: Boolean = true,
    thumbWidth: Dp = 3.dp,
    trackPadding: Dp = 1.dp,
    trackColor: Color = BongBaekTheme.colors.bgDisplayRange,
    thumbColor: Color = BongBaekTheme.colors.btnInteractiveTertiary,
    trackCornerRadius: Dp = 4.dp,
    thumbCornerRadius: Dp = 2.dp,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
    endPadding: Dp = 0.dp,
): Modifier {
    return drawWithContent {
        drawContent()

        val trackRadius = trackCornerRadius.toPx()
        val thumbRadius = thumbCornerRadius.toPx()

        val thumbWidthPx = thumbWidth.toPx()

        val trackPaddingPx = trackPadding.toPx()
        val topPaddingPx = topPadding.toPx()
        val bottomPaddingPx = bottomPadding.toPx()
        val endPaddingPx = endPadding.toPx()

        val viewportHeight = this.size.height
        val viewportWidth = this.size.width

        val totalContentHeight = scrollState.maxValue.toFloat() + viewportHeight
        val scrollValue = scrollState.value.toFloat()

        val trackWidth = thumbWidthPx + (trackPaddingPx * 2)

        val trackHeight = viewportHeight - topPaddingPx - bottomPaddingPx

        val thumbSpace = trackHeight - (trackPaddingPx * 2)

        val thumbHeight =
            (viewportHeight / totalContentHeight) * thumbSpace

        val trackStartX = viewportWidth - endPaddingPx - trackWidth
        val trackStartY = topPaddingPx

        val thumbStartX = trackStartX + trackPaddingPx
        val thumbStartY =
            (scrollValue / totalContentHeight) * thumbSpace + topPaddingPx + trackPaddingPx

        // Track
        if (showScrollBarTrack) {
            drawRoundRect(
                cornerRadius = CornerRadius(trackRadius),
                color = trackColor,
                topLeft = Offset(
                    x = trackStartX,
                    y = trackStartY,
                ),
                size = Size(
                    width = trackWidth,
                    height = trackHeight,
                ),
            )
        }

        // Thumb
        drawRoundRect(
            cornerRadius = CornerRadius(thumbRadius),
            color = thumbColor,
            topLeft = Offset(
                x = thumbStartX,
                y = thumbStartY,
            ),
            size = Size(
                width = thumbWidthPx,
                height = thumbHeight,
            )
        )
    }
}
