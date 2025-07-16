package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.edit_save_button
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun EditSaveButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = BongBaekTheme.colors.primaryNormal,
                contentColor = BongBaekTheme.colors.white,
                disabledContainerColor = BongBaekTheme.colors.gray700,
                disabledContentColor = BongBaekTheme.colors.gray500,
            ),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                text = stringResource(edit_save_button),
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }
    }
}