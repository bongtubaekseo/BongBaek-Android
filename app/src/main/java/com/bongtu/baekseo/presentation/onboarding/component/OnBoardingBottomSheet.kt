package com.bongtu.baekseo.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.R.string.button_next
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_age
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_all
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_privacy
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_service
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_description
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.CheckBoxType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.checkbox.BongBaekCheckBox
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.onboarding.model.OnBoardingAgree
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingBottomSheet(
    items: List<OnBoardingAgree>,
    checkedStates: List<Boolean>,
    onAllCheckedChange: (Boolean) -> Unit,
    onItemCheckedChange: (index: Int, checked: Boolean) -> Unit,
    onNextClick: () -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        dragHandle = null,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
        ),
    ) {
        OnBoardingBottomSheetAgreeContent(
            items = items,
            checkedStates = checkedStates,
            onAllCheckedChange = onAllCheckedChange,
            onItemCheckedChange = onItemCheckedChange,
            onNextClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .navigationBarsPadding(),
        )
    }
}

@Composable
private fun OnBoardingBottomSheetAgreeContent(
    items: List<OnBoardingAgree>,
    checkedStates: List<Boolean>,
    onAllCheckedChange: (Boolean) -> Unit,
    onItemCheckedChange: (index: Int, checked: Boolean) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isAllChecked = checkedStates.all { it }
    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray750)
            .padding(horizontal = 20.dp),
    ) {
        Text(
            text = stringResource(id = onboarding_bottom_sheet_description),
            modifier = Modifier.padding(top = 40.dp),
            style = BongBaekTheme.typography.titleSemiBold18,
            color = BongBaekTheme.colors.white,
        )

        Spacer(modifier = Modifier.size(30.dp))

        OnBoardingAgreeItem(
            item = OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_all,
                isDescription = true,
                isArrowVisible = false,
            ),
            isChecked = isAllChecked,
            onCheckedChange = onAllCheckedChange,
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(vertical = 18.dp),
            color = BongBaekTheme.colors.lineNormal,
        )

        items.forEachIndexed { index, item ->
            OnBoardingAgreeItem(
                item = item,
                isChecked = checkedStates[index],
                onCheckedChange = { checked ->
                    onItemCheckedChange(index, checked)
                },
            )
            if (index < items.lastIndex) {
                Spacer(modifier = Modifier.size(14.dp))
            }
        }

        BongBaekButton(
            title = stringResource(id = button_next),
            onClick = onNextClick,
            buttonType = ButtonType.PRIMARY,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            enabled = isAllChecked,
        )
    }
}

@Composable
private fun OnBoardingAgreeItem(
    item: OnBoardingAgree,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit = {},
) {
    val (textStyle, textColor) = if (item.isDescription) {
        BongBaekTheme.typography.titleSemiBold16 to BongBaekTheme.colors.gray200
    } else {
        BongBaekTheme.typography.body2Regular16 to BongBaekTheme.colors.gray300
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BongBaekCheckBox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            checkBoxType = CheckBoxType.PRIMARY,
        )

        Text(
            text = stringResource(id = item.titleRes),
            modifier = Modifier.padding(start = 8.dp),
            style = textStyle,
            color = textColor,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (item.isArrowVisible) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.noRippleClickable(onClick = onIconClick),
                tint = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingBottomSheetAgreeContentPreview() {
    val checkedStates = remember { mutableStateListOf(false, false, false) }

    val items = remember {
        mutableStateListOf(
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_age,
                isDescription = false,
                isArrowVisible = false,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_service,
                isDescription = false,
                isArrowVisible = true,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_privacy,
                isDescription = false,
                isArrowVisible = true,
            ),
        )
    }

    BongBaekTheme {
        OnBoardingBottomSheetAgreeContent(
            items = items,
            checkedStates = checkedStates,
            onAllCheckedChange = { checked ->
                checkedStates.indices.forEach { index ->
                    checkedStates[index] = checked
                }
            },
            onItemCheckedChange = { index, checked ->
                checkedStates[index] = checked
            },
            onNextClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun OnBoardingBottomSheetPreview() {
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    val items = remember {
        mutableStateListOf(
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_age,
                isDescription = false,
                isArrowVisible = false,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_service,
                isDescription = false,
                isArrowVisible = true,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_privacy,
                isDescription = false,
                isArrowVisible = true,
            ),
        )
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val showBottomSheet by remember { mutableStateOf(true) }

    BongBaekTheme {
        if (showBottomSheet) {
            OnBoardingBottomSheet(
                items = items,
                checkedStates = checkedStates,
                onAllCheckedChange = { checked ->
                    checkedStates.indices.forEach { index ->
                        checkedStates[index] = checked
                    }
                },
                onItemCheckedChange = { index, checked ->
                    checkedStates[index] = checked
                },
                onNextClick = {},
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState,
            )
        }
    }
}
