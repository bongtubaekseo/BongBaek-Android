package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_search
import com.bongtu.baekseo.R.string.search_text_field_placeholder
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 *  Search Text Field
 *
 *  @param text 입력값
 *  @param onTextChange 입력값 변경
 *  @param onItemSelected 외부 아이템 선텍시 호출되는 콜백
 */
@Composable
fun SearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onItemSelected: (() -> Unit)? = null,
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp),
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(size = 10.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(onItemSelected) {
        onItemSelected?.let { handler ->
            handler.invoke()
            focusManager.clearFocus()
        }
    }

    val bongBaekColors = BongBaekTheme.colors
    val textColor = remember(isFocused) {
        if (isFocused) bongBaekColors.primaryNormal else bongBaekColors.white
    }

    Row(
        modifier = modifier
            .padding(paddingValues)
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = roundedCornerShape,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_search),
            contentDescription = null,
            tint = BongBaekTheme.colors.white,
            modifier = Modifier.padding(end = 12.dp),
        )

        BongBaekInnerTextField(
            text = text,
            onTextChange = onTextChange,
            textColor = textColor,
            placeholder = stringResource(id = search_text_field_placeholder),
            placeholderColor = BongBaekTheme.colors.gray500,
            textStyle = BongBaekTheme.typography.body2Regular16,
            interactionSource = interactionSource,
            cursorColor = BongBaekTheme.colors.transparent,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.black),
        verticalArrangement = Arrangement.Center,
    ) {
        var text by remember { mutableStateOf("봉봉") }
        var selectHandler: (() -> Unit)? by remember { mutableStateOf(null) }

        SearchTextField(
            text = text,
            onTextChange = { text = it },
            onItemSelected = selectHandler
        )

        Text(
            text = "드롭다운 선택: 테스트 입력",
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(Color.DarkGray)
                .padding(8.dp)
                .noRippleClickable {
                    selectHandler = {
                        text = "테스트 입력"
                        selectHandler = null
                    }
                }
        )
    }
}
