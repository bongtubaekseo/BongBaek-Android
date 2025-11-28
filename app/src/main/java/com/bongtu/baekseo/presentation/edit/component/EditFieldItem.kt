package com.bongtu.baekseo.presentation.edit.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.edit_name_title
import com.bongtu.baekseo.R.string.edit_required_text
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun EditFieldItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    isDimmed: Boolean = false,
    trailing: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    val (textColor, requiredTextColor) =
        if (isDimmed) BongBaekTheme.colors.txtDisplayTertiary to BongBaekTheme.colors.txtDisplayTertiary
        else BongBaekTheme.colors.txtDisplaySecondary to BongBaekTheme.colors.statusFocused

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(id = labelRes),
                style = BongBaekTheme.typography.body1Medium14,
                color = textColor,
            )

            if (isRequired) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = edit_required_text),
                    style = BongBaekTheme.typography.body1Medium14,
                    color = requiredTextColor,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            trailing?.invoke()
        }

        content?.let {
            Spacer(modifier = Modifier.height(12.dp))
            it.invoke()
        }
    }
}

@Preview
@Composable
private fun EditFieldItemPreview() {
    BongBaekTheme {
        EditFieldItem(
            iconRes = ic_person,
            labelRes = edit_name_title,
            content = {
                Text(
                    text = "홍길동",
                    style = BongBaekTheme.typography.body1Medium14,
                    color = BongBaekTheme.colors.txtFieldValue,
                )
            },
        )
    }
}
