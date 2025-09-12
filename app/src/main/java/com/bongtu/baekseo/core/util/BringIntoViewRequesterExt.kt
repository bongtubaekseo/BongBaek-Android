package com.bongtu.baekseo.core.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.ui.focus.FocusState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * [isFocused]에 따라 [BringIntoViewRequester]가 붙은 컴포넌트를 화면 안으로 가져오는 메소드
 *
 * @param scope [BringIntoViewRequester.bringIntoView]가 실행되는 스코프
 * @param isFocused 포커스 상태 불리언 값
 * @param delayMillis [BringIntoViewRequester.bringIntoView] 호출을 지연시키는 시간 (밀리초 단위)
 */
@OptIn(ExperimentalFoundationApi::class)
fun BringIntoViewRequester.bringIntoView(
    scope: CoroutineScope,
    isFocused: Boolean,
    delayMillis: Long = 200L,
) {
    if (isFocused) {
        scope.launch {
            delay(delayMillis)
            bringIntoView()
        }
    }
}