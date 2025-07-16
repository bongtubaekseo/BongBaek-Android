package com.bongtu.baekseo.presentation.home.schedule.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.img_record_empty
import com.bongtu.baekseo.R.string.record_empty_button
import com.bongtu.baekseo.R.string.record_empty_description
import com.bongtu.baekseo.R.string.schedule_empty_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

private const val POSTPOSITION_GA = "가"
private const val POSTPOSITION_E = "이"

@Composable
fun ScheduleEmptyContent(
    eventType: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val postposition = remember(eventType) {
        if (eventType == "돌잔치") POSTPOSITION_GA else POSTPOSITION_E
    }
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(schedule_empty_title, eventType, postposition),
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.headBold24,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        Text(
            text = stringResource(record_empty_description),
            color = BongBaekTheme.colors.gray400,
            style = BongBaekTheme.typography.body2Regular14,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
        )

        Image(
            painter = painterResource(img_record_empty),
            contentDescription = null,
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = onButtonClick,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BongBaekTheme.colors.primaryNormal,
                contentColor = BongBaekTheme.colors.white,
            ),
            contentPadding = PaddingValues(
                horizontal = 30.dp,
                vertical = 8.dp,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(record_empty_button),
                    style = BongBaekTheme.typography.titleSemiBold16,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleEmptyContentPreview() {
    BongBaekTheme {
        ScheduleEmptyContent(
            eventType = EventCategoryType.BIRTHDAY.label,
            onButtonClick = {},
            modifier = Modifier,
        )
    }
}