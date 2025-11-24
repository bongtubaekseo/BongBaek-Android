package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Immutable
private data class TabStyle(
    val textColor: Color,
    val textStyle: TextStyle,
    val backgroundColor: Color,
    val lineColor: Color,
)

@Composable
fun AttendTypeTab(
    selectedTab: AttendType,
    onTabClick: (AttendType) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val (color, typography) = BongBaekTheme.colors to BongBaekTheme.typography
    val selectedTabStyle = TabStyle(
        textColor = color.txtInteractivePrimary,
        textStyle = typography.titleSemiBold16,
        backgroundColor = color.btnInteractiveDisabled,
        lineColor = color.statusFocused,
    )
    val defaultTabStyle = TabStyle(
        textColor = color.txtStatusDisabled,
        textStyle = typography.body2Regular16,
        backgroundColor = color.transparent,
        lineColor = color.transparent,
    )

    Box(
        modifier = modifier,
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = BongBaekTheme.colors.borderDividerPrimary,
            modifier = Modifier.align(Alignment.BottomCenter),
        )

        Row {
            AttendType.entries.forEach { type ->
                val isSelected = type == selectedTab
                val style =
                    remember(isSelected) { if (isSelected) selectedTabStyle else defaultTabStyle }

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = style.backgroundColor,
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                ),
                            )
                            .noRippleClickable {
                                if (isEnabled && selectedTab != type) onTabClick(type)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = type.label,
                            style = style.textStyle,
                            color = style.textColor,
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                ),
                        )
                    }

                    HorizontalDivider(
                        thickness = 2.dp,
                        color = style.lineColor,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AttendTypeTabPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AttendTypeTab(
                selectedTab = AttendType.ATTEND,
                onTabClick = {},
                isEnabled = true,
            )
        }
    }
}
