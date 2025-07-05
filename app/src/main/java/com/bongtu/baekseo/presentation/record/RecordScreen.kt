package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecordRoute(
    modifier: Modifier = Modifier,
) {
    RecordScreen(
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Record",
        )
    }
}

@Preview
@Composable
private fun RecordScreenPreview() {
    BongBaekTheme {
        RecordScreen(

        )
    }
}