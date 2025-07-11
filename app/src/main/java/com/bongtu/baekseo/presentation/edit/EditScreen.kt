package com.bongtu.baekseo.presentation.edit

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_arrow_down
import com.bongtu.baekseo.R.drawable.ic_arrow_up
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_check_gray
import com.bongtu.baekseo.R.drawable.ic_event
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.drawable.ic_relation
import com.bongtu.baekseo.R.string.edit_date_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_date_title
import com.bongtu.baekseo.R.string.edit_event_dropdown_placeholder
import com.bongtu.baekseo.R.string.edit_event_title
import com.bongtu.baekseo.R.string.edit_is_attend_dropdown_placeholder
import com.bongtu.baekseo.R.string.edit_is_attend_title
import com.bongtu.baekseo.R.string.edit_location_add_text
import com.bongtu.baekseo.R.string.edit_location_edit_text
import com.bongtu.baekseo.R.string.edit_location_title
import com.bongtu.baekseo.R.string.edit_name_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_name_title
import com.bongtu.baekseo.R.string.edit_nickname_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_nickname_title
import com.bongtu.baekseo.R.string.edit_relation_dropdown_placeholder
import com.bongtu.baekseo.R.string.edit_relation_title
import com.bongtu.baekseo.R.string.edit_required_text
import com.bongtu.baekseo.R.string.edit_save_button
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.edit.component.EditCostLabelTextField
import com.bongtu.baekseo.presentation.edit.component.EditLocationContent
import com.bongtu.baekseo.presentation.edit.component.EditMemoContent
import com.bongtu.baekseo.presentation.edit.type.EditType
import com.bongtu.baekseo.presentation.record.type.AttendType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun EditRoute(
    modifier: Modifier = Modifier,
) {
    // TODO: previousBackStackEntry?.destination?.route 로 분기 처리 예정
    val editType = EditType.EDIT
    when (editType) {
        EditType.EDIT -> EditScreen(
            topBarTitle = EditType.EDIT.title,
            modifier = modifier,
        )

        EditType.ADD -> EditScreen(
            topBarTitle = EditType.ADD.title,
            modifier = modifier,
        )
    }
}

@Composable
private fun EditScreen(
    topBarTitle: String,
    modifier: Modifier = Modifier,
) {
    // TODO: UiState 사용 예정
    var name by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var relationSelectedItem by remember { mutableStateOf("") }
    var eventSelectedItem by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var attendanceSelectedItem by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }
    var dialogDate by remember { mutableStateOf("") }
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }

    var location by remember { mutableStateOf(null) } // TODO: LocationInfo 변경 예정
    var memo by remember { mutableStateOf("") }
    var validateResult: TextFieldValidateResult by remember {
        mutableStateOf(
            TextFieldValidateResult.Default
        )
    }

    val relations = RelationType.entries.map { it.label }.toImmutableList()
    val events = EventType.entries.map { it.label }.toImmutableList()
    val attendOptions = AttendType.entries.map { it.label }.toImmutableList() // TODO: 타입 위치 변경 예정

    val isFormFilled = name.isNotBlank()
            && nickName.isNotBlank()
            && relationSelectedItem.isNotBlank()
            && eventSelectedItem.isNotBlank()
            && cost.isNotBlank()
            && attendanceSelectedItem.isNotBlank()
            && date.isNotBlank()

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.gray900,
            )
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = topBarTitle,
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable {
                            // TODO: navigateUp 예정
                        },
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = BongBaekTheme.colors.gray800,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(vertical = 24.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                LabelTextField(
                    labelName = stringResource(id = edit_name_title),
                    labelImage = ic_person,
                    text = name,
                    placeholder = stringResource(id = edit_name_text_field_placeholder),
                    modifier = Modifier,
                    validateResult = validateResult,
                    onTextChange = {
                        name = it
                        validateResult = TextFieldValidateResult.Default
                    },
                    onInputDone = {
                        // TODO: 유효성 검증 로직
                    },
                    isRequired = true,
                )

                LabelTextField(
                    labelName = stringResource(id = edit_nickname_title),
                    labelImage = ic_nickname,
                    text = nickName,
                    placeholder = stringResource(id = edit_nickname_text_field_placeholder),
                    validateResult = validateResult,
                    onTextChange = {
                        nickName = it
                        validateResult = TextFieldValidateResult.Default
                    },
                    onInputDone = {
                        // TODO: 유효성 검증 로직
                    },
                    isRequired = true,
                )

                FormFieldItem(
                    iconRes = ic_relation,
                    labelRes = edit_relation_title,
                    content = {
                        FormFieldDropDown(
                            placeholder = stringResource(id = edit_relation_dropdown_placeholder),
                            menuItems = relations,
                            selectedItem = relationSelectedItem,
                            onItemSelected = { relationSelectedItem = it },
                        )
                    },
                )

                FormFieldItem(
                    iconRes = ic_event,
                    labelRes = edit_event_title,
                    content = {
                        FormFieldDropDown(
                            placeholder = stringResource(id = edit_event_dropdown_placeholder),
                            menuItems = events,
                            selectedItem = eventSelectedItem,
                            onItemSelected = { eventSelectedItem = it },
                        )
                    }
                )

                EditCostLabelTextField(
                    text = cost,
                    validateResult = validateResult,
                    onTextChange = {
                        cost = it
                        validateResult = TextFieldValidateResult.Default
                    },
                    onInputDone = {
                        // TODO: 유효성 검증 로직
                    },
                )

                FormFieldItem(
                    iconRes = ic_check_gray,
                    labelRes = edit_is_attend_title,
                    content = {
                        FormFieldDropDown(
                            placeholder = stringResource(id = edit_is_attend_dropdown_placeholder),
                            menuItems = attendOptions,
                            selectedItem = attendanceSelectedItem,
                            onItemSelected = { attendanceSelectedItem = it },
                        )
                    }
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = edit_date_title),
                    text = date,
                    placeholder = stringResource(id = edit_date_text_field_placeholder),
                    modifier = Modifier
                        .noRippleClickable {
                            dialogDate = date
                            isDatePickerDialogVisible = true
                        },
                    isEditable = false,
                    isClearButtonEnabled = false,
                    visualTransformation = DateTextFieldFormat(),
                )

                FormFieldItem(
                    iconRes = ic_location,
                    labelRes = edit_location_title,
                    content = {
                        if (location != null) {  // TODO: LocationInfo 변경 예정
                            EditLocationContent(
                                location = location.toString(),
                                address = "",
                            )
                        }
                    },
                    isRequired = false,
                    trailing = {
                        Text(
                            text = stringResource(
                                if (location == null) edit_location_add_text
                                else edit_location_edit_text
                            ),
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.gray300,
                            modifier = Modifier
                                .noRippleClickable(
                                    onClick = { /* TODO: 지도 추가 화면 이동 */ }
                                )
                        )
                    }
                )
            }

            EditMemoContent(
                text = memo,
                onTextChange = { memo = it },
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 160.dp,
                    )
            )
        }
        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.DATE,
                value = dialogDate,
                onValueChange = {
                    dialogDate = it
                },
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                },
                onConfirmClick = {
                    isDatePickerDialogVisible = false
                    date = dialogDate
                },
            )
        }
    }

    SaveButton(
        onClick = {
            // TODO: 저장 로직
        },
        enabled = isFormFilled,
        modifier = Modifier
            .padding(
                top = 10.dp,
                bottom = 36.dp,
                start = 20.dp,
                end = 20.dp,
            )
            .systemBarsPadding(),
    )
}

@Composable
private fun FormFieldItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    trailing: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .height(16.dp),
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(id = labelRes),
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.white,
            )

            if (isRequired) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = edit_required_text),
                    style = BongBaekTheme.typography.body1Medium14,
                    color = BongBaekTheme.colors.primaryNormal,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            trailing?.invoke()
        }
        content.invoke()
    }
}

@Composable
private fun FormFieldDropDown(
    placeholder: String,
    menuItems: ImmutableList<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val isSelected = selectedItem.isNotBlank()

    val bongBaekColors = BongBaekTheme.colors
    val text = if (isSelected) selectedItem else placeholder
    val textColor = when {
        isSelected && expanded -> bongBaekColors.primaryNormal
        isSelected || expanded -> bongBaekColors.white
        else -> bongBaekColors.gray300
    }
    val borderColor =
        if (expanded) BongBaekTheme.colors.primaryNormal else BongBaekTheme.colors.transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            )
            .noRippleClickable { expanded = !expanded },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = BongBaekTheme.typography.body1Medium16,
            color = textColor,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(id = if (expanded) ic_arrow_up else ic_arrow_down),
            contentDescription = null,
            tint = textColor,
        )
    }

    BongBaekDropdownMenu(
        expanded = expanded,
        items = menuItems,
        maxItemSize = menuItems.size,
        selectedItem = selectedItem,
        onDismissRequest = { expanded = false },
        onItemSelected = { onItemSelected(it) },
        modifier = Modifier
            .padding(top = 12.dp),
    )
}

@Composable
private fun SaveButton(
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

@Preview
@Composable
private fun EditScreenPreview() {
    BongBaekTheme {
        EditScreen(
            topBarTitle = EditType.EDIT.title,
        )
    }
}