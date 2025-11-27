package com.bongtu.baekseo.core.util

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
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
 * 포커스된 컴포저블을 키보드에 가리지 않도록 컴포넌트 영역 안으로 이동시키는 함수
 * @param isFocused  bring-into-view 동작 실행 여부를 결정하는 상태 값
 * @param extraBottom  화면 이동 시 아래로 확보할 추가 여백 값(dp)
 * @param delayMillis  키보드 표시 이후 동작 실행까지 대기할 지연 시간 값(ms)
 */
fun Modifier.bringIntoViewOnFocus(
    isFocused: Boolean,
    extraBottom: Dp,
    delayMillis: Long = 400L,
): Modifier = composed {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isImeVisible = imeBottom > 0
    val extraBottomPx = with(density) { extraBottom.toPx() }

    LaunchedEffect(isImeVisible) {
        if (!isFocused) return@LaunchedEffect
        val coords = layoutCoordinates ?: return@LaunchedEffect

        val original = coords.boundsInParent()
        val targetRect = Rect(
            left = original.left,
            top = original.top,
            right = original.right,
            bottom = original.bottom + extraBottomPx,
        )

        delay(delayMillis)
        bringIntoViewRequester.bringIntoView(targetRect)
    }

    this
        .bringIntoViewRequester(bringIntoViewRequester)
        .onGloballyPositioned { layoutCoordinates = it }
}
