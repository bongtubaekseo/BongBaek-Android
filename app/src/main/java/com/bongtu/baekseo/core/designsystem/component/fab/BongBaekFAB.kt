package com.bongtu.baekseo.core.designsystem.component.fab

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun BongBaekFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.ic_arrow_up,
    shape: Shape = CircleShape,
    containerColor: Color = BongBaekTheme.colors.btnInteractiveSecondary.copy(alpha = 0.7f),
    borderColor: Color = BongBaekTheme.colors.borderFieldDefault.copy(alpha = 0.7f),
    contentColor: Color = BongBaekTheme.colors.iconInteractiveDefault,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape,
            ),
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun BongBaekFABPreview() {
    BongBaekTheme {
        BongBaekFAB(
            onClick = { }
        )
    }
}
