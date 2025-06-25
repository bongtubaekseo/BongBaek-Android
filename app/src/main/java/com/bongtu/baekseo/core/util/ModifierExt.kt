package com.bongtu.baekseo.core.util

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Material3 기본 리플 모션 제거 함수
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
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
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
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
