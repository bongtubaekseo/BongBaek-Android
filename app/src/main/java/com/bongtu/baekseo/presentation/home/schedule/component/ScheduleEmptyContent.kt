package com.bongtu.baekseo.presentation.home.schedule.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
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

        BongBaekButton(
            title = stringResource(record_empty_button),
            onClick = onButtonClick,
            buttonType = ButtonType.PRIMARY,
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 30.dp),
            textStyle = BongBaekTheme.typography.titleSemiBold16,
            paddingValues = PaddingValues(
                horizontal = 30.dp,
                vertical = 8.dp,
            ),
        )
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