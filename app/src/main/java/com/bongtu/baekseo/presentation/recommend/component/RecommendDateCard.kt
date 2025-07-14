package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.string.date_picker_description_date
import com.bongtu.baekseo.R.string.recommendation_date_title
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.textfield.RoundedBoxTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendDateCard(
    date: String,
    text: String,
    onTextChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_calendar),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = BongBaekTheme.colors.primaryNormal,
            )

            Text(
                text = stringResource(recommendation_date_title),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.gray100,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RoundedBoxTextField(
            text = date,
            placeholder = stringResource(date_picker_description_date),
            isEditable = false,
            onClick = {
                isDialogOpen = true
            },
        )

        if (isDialogOpen) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.DATE,
                value = text,
                onValueChange = onTextChange,
                onDismissRequest = {
                    isDialogOpen = false
                    onTextChange("")
                },
                onConfirmClick = onConfirmClick,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendDateCardPreview() {
    BongBaekTheme {
        RecommendDateCard(
            date = "",
            text = "",
            onTextChange = {},
            onConfirmClick = {},
        )
    }
}
