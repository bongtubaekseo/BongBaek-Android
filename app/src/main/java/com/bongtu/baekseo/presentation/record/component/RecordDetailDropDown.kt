package com.bongtu.baekseo.presentation.record.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_up
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_check_gray
import com.bongtu.baekseo.R.drawable.ic_coin
import com.bongtu.baekseo.R.drawable.ic_event
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.drawable.ic_relation
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.record.type.RecordDetailDropDownTrailingType
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate


@Composable
fun RecordDetailDropDown(
    hostName: String,
    hostNickName: String,
    relationship: String,
    category: String,
    costLabel: String,
    attendLabel: String,
    formattedEventDate: String,
    location: String? = null,
    address: String? = null,
) {
    var isDetailVisible by remember { mutableStateOf(false) }

    // TODO: 변경 예정
    val dropDownItems = persistentListOf(
        Triple(
            ic_person, "이름",
            RecordDetailDropDownTrailingType.TrailingText(hostName)
        ),
        Triple(
            ic_nickname, "별명",
            RecordDetailDropDownTrailingType.TrailingText(hostNickName)
        ),
        Triple(
            ic_relation, "관계",
            RecordDetailDropDownTrailingType.TrailingChip(relationship)
        ),
        Triple(
            ic_event, "경조사",
            RecordDetailDropDownTrailingType.TrailingChip(category)
        ),
        Triple(
            ic_coin, "경조사비",
            RecordDetailDropDownTrailingType.TrailingText(costLabel)
        ),
        Triple(
            ic_check_gray, "참석 여부", RecordDetailDropDownTrailingType.TrailingText(attendLabel)
        ),
        Triple(
            ic_calendar, "날짜",
            RecordDetailDropDownTrailingType.TrailingText(formattedEventDate)
        ),
        Triple(
            ic_location, "행사장",
            null
        ),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "상세정보",
            style = BongBaekTheme.typography.titleSemiBold18,
            color = BongBaekTheme.colors.white,
            modifier = Modifier.padding(vertical = 20.dp),
        )
        Icon(
            imageVector = ImageVector.vectorResource(ic_arrow_up),
            contentDescription = null,
            modifier = Modifier
                .padding(14.dp)
                .size(20.dp)
                .noRippleClickable({ isDetailVisible != isDetailVisible }),
            tint = BongBaekTheme.colors.white,
        )
    }


    AnimatedVisibility(
        visible = isDetailVisible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BongBaekTheme.colors.gray800,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            dropDownItems.forEach { (iconRes, label, type) ->
                RecordDetailDropDownItem(
                    iconRes = iconRes,
                    label = label,
                    trailingType = type,
                )

            }
        }
    }
}

@Composable
private fun RecordDetailDropDownItem(
    @DrawableRes iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
    trailingType: RecordDetailDropDownTrailingType? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(16.dp)
                    .padding(end = 4.dp),
                tint = BongBaekTheme.colors.gray400,
            )
            Text(
                text = label,
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.gray100,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        trailingType?.let {
            when (trailingType) {
                is RecordDetailDropDownTrailingType.TrailingChip ->
                    Row(
                        modifier = modifier
                            .background(
                                color = BongBaekTheme.colors.primaryBackground,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 3.dp,
                            ),
                    ) {
                        Text(
                            text = trailingType.text,
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.primaryNormal,
                        )
                    }

                is RecordDetailDropDownTrailingType.TrailingText ->
                    Text(
                        text = trailingType.text,
                        style = BongBaekTheme.typography.body1Medium16,
                        color = BongBaekTheme.colors.white,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
            }
        }
    }
}

@Preview
@Composable
private fun RecordDetailDropDownPreview() {
    BongBaekTheme {
        RecordDetailDropDown(
            hostName = "김봉백",
            hostNickName = "봉봉",
            category = "경조사",
            relationship = "가족",
            costLabel = "10000원",
            attendLabel = "참석",
            formattedEventDate = LocalDate.now().toString(),
        )
    }
}