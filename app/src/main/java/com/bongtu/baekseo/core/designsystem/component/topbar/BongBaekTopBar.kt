package com.bongtu.baekseo.core.designsystem.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_delete
import com.bongtu.baekseo.R.drawable.ic_edit
import com.bongtu.baekseo.R.drawable.ic_plus
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

/**
 * Bong baek top bar
 *
 * @param title - TopBar 제목
 * @param topBarType - 제목의 좌, 우측 혹은 중앙 정렬을 위한 type
 * @param modifier
 * @param leadingIcon - 좌측 icon
 * @param trailingIcon - 우측 icon
 */
@Composable
fun BongBaekTopBar(
    title: String,
    topBarType: TopBarType,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BongBaekTheme.typography.titleSemiBold18,
    textColor: Color = BongBaekTheme.colors.white,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(topBarType.padding),
        horizontalArrangement = topBarType.arrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.let { icon ->
            icon()
            Spacer(modifier = Modifier.width(6.dp))
        }

        Text(
            text = title,
            style = textStyle,
            color = textColor,
        )

        trailingIcon?.let { icon ->
            Spacer(modifier = Modifier.weight(1f))
            icon()
        }
    }
}

@Preview
@Composable
private fun BongBaekTopBarPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val modifier = Modifier
                .padding(14.dp)
                .size(20.dp)

            BongBaekTopBar(
                title = "프로필 설정",
                topBarType = TopBarType.TEXT_ONLY_START,
            )

            BongBaekTopBar(
                title = "관계 정보",
                topBarType = TopBarType.TEXT_ONLY_CENTER,
            )

            BongBaekTopBar(
                title = "경조사 전체 기록",
                topBarType = TopBarType.LEADING_ICON,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_arrow_back),
                        contentDescription = null,
                        modifier = modifier,
                        tint = BongBaekTheme.colors.white,
                    )
                }
            )

            BongBaekTopBar(
                title = "경조사 상세조회",
                topBarType = TopBarType.BOTH_ICONS,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_arrow_back),
                        contentDescription = null,
                        modifier = modifier,
                        tint = BongBaekTheme.colors.white,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_edit),
                        contentDescription = null,
                        modifier = modifier,
                        tint = BongBaekTheme.colors.white,
                    )
                }
            )

            BongBaekTopBar(
                title = "경조사 전체 기록",
                topBarType = TopBarType.TRAILING_ICON,
                trailingIcon = {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_plus),
                            contentDescription = null,
                            modifier = modifier,
                            tint = BongBaekTheme.colors.gray400,
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_delete),
                            contentDescription = null,
                            modifier = modifier,
                            tint = BongBaekTheme.colors.gray400,
                        )
                    }
                }
            )
        }
    }
}
