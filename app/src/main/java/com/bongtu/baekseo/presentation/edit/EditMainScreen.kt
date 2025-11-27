package com.bongtu.baekseo.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_record_calendar_off
import com.bongtu.baekseo.R.drawable.ic_record_calendar_on
import com.bongtu.baekseo.R.drawable.ic_record_check_off
import com.bongtu.baekseo.R.drawable.ic_record_check_on
import com.bongtu.baekseo.R.drawable.ic_record_event_off
import com.bongtu.baekseo.R.drawable.ic_record_event_on
import com.bongtu.baekseo.R.drawable.ic_record_location_off
import com.bongtu.baekseo.R.drawable.ic_record_location_on
import com.bongtu.baekseo.R.drawable.ic_record_money_on
import com.bongtu.baekseo.R.drawable.ic_record_nickname_off
import com.bongtu.baekseo.R.drawable.ic_record_nickname_on
import com.bongtu.baekseo.R.drawable.ic_record_person_off
import com.bongtu.baekseo.R.drawable.ic_record_person_on
import com.bongtu.baekseo.R.drawable.ic_record_relation_off
import com.bongtu.baekseo.R.drawable.ic_record_relation_on
import com.bongtu.baekseo.R.string.edit_cost_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_cost_title
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
import com.bongtu.baekseo.R.string.edit_save_button
import com.bongtu.baekseo.R.string.kr_won
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.CostTextFieldFormat
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.clearFocus
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.NavigateToFinal
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.NavigateToLocation
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.NavigateToRecord
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import com.bongtu.baekseo.presentation.edit.component.EditDropdownMenu
import com.bongtu.baekseo.presentation.edit.component.EditFieldItem
import com.bongtu.baekseo.presentation.edit.component.EditLocationContent
import com.bongtu.baekseo.presentation.edit.component.EditMemoContent
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import kotlinx.collections.immutable.toImmutableList

@Composable
fun EditMainRoute(
    editEntryType: EditEntryType,
    navigateUp: () -> Unit,
    navigateToFinal: () -> Unit,
    navigateToEditLocation: () -> Unit,
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToRecord -> navigateUp()
                    is NavigateToDetail -> navigateUp()
                    is NavigateToFinal -> navigateToFinal()
                    is NavigateToLocation -> navigateToEditLocation()
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.updateEntryType(editEntryType)
    }

    EditMainScreen(
        editEntryType = editEntryType,
        uiState = uiState,
        isFromResult = editEntryType == EditEntryType.FROM_RESULT,
        navigateUp = navigateUp,
        navigateToLocation = viewModel::navigateToLocation,
        onNameChange = viewModel::updateName,
        onNicknameChange = viewModel::updateNickname,
        onRelationSelect = viewModel::updateRelationship,
        onEventSelect = viewModel::updateEventCategory,
        onCostChange = viewModel::updateCost,
        onAttendSelect = viewModel::updateAttendLabel,
        onDateChange = viewModel::updateEventDate,
        onNoteChange = viewModel::updateNote,
        checkIsFormFilled = viewModel::updateButtonState,
        onSubmitEventButtonClick = { viewModel.submitEventInformation(editEntryType) },
        modifier = modifier,
    )
}

@Composable
private fun EditMainScreen(
    editEntryType: EditEntryType,
    uiState: EditUiState,
    isFromResult: Boolean,
    navigateUp: () -> Unit,
    navigateToLocation: () -> Unit,
    onNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onRelationSelect: (String) -> Unit,
    onEventSelect: (String) -> Unit,
    onCostChange: (String) -> Unit,
    onAttendSelect: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    checkIsFormFilled: () -> Boolean,
    onSubmitEventButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var dialogText by remember { mutableStateOf("") }
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    val relations = RelationType.entries.map { it.label }.toImmutableList()
    val events = EventType.entries.map { it.label }.toImmutableList()
    val attendOptions = AttendType.entries.map { it.label }.toImmutableList()

    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val isImeVisible = WindowInsets.ime.getBottom(density) > 0

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgDisplayPrimary,
            )
            .statusBarsPadding()
            .clearFocus(focusManager),
    ) {
        BongBaekTopBar(
            title = editEntryType.editType.title,
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar.excludeTop()),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(
                        color = BongBaekTheme.colors.bgDisplaySecondary,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 24.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                LabelTextField(
                    labelName = stringResource(id = edit_name_title),
                    labelImage = if (isFromResult) ic_record_person_off else ic_record_person_on,
                    text = uiState.name,
                    placeholder = stringResource(id = edit_name_text_field_placeholder),
                    errorText = uiState.nameError,
                    onTextChange = onNameChange,
                    isRequired = true,
                    isEditable = !isFromResult,
                    isDimmed = isFromResult,
                )

                LabelTextField(
                    labelName = stringResource(id = edit_nickname_title),
                    labelImage = if (isFromResult) ic_record_nickname_off else ic_record_nickname_on,
                    text = uiState.nickname,
                    placeholder = stringResource(id = edit_nickname_text_field_placeholder),
                    errorText = uiState.nicknameError,
                    onTextChange = onNicknameChange,
                    isRequired = true,
                    isEditable = !isFromResult,
                    isDimmed = isFromResult,
                )

                EditFieldItem(
                    iconRes = if (isFromResult) ic_record_relation_off else ic_record_relation_on,
                    labelRes = edit_relation_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_relation_dropdown_placeholder),
                            menuItems = relations,
                            selectedItem = uiState.relationship,
                            onItemSelected = onRelationSelect,
                            isEditable = !isFromResult,
                        )
                    },
                )

                EditFieldItem(
                    iconRes = if (isFromResult) ic_record_event_off else ic_record_event_on,
                    labelRes = edit_event_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_event_dropdown_placeholder),
                            menuItems = events,
                            selectedItem = uiState.eventCategory,
                            onItemSelected = onEventSelect,
                            isEditable = !isFromResult,
                        )
                    },
                )

                LabelTextField(
                    labelName = stringResource(id = edit_cost_title),
                    labelImage = ic_record_money_on,
                    text = uiState.cost,
                    placeholder = stringResource(id = edit_cost_text_field_placeholder),
                    errorText = uiState.costError,
                    onTextChange = onCostChange,
                    isRequired = true,
                    keyboardType = KeyboardType.NumberPassword,
                    visualTransformation = CostTextFieldFormat(),
                    extraBottomPadding = 24.dp,
                ) {
                    Text(
                        text = stringResource(kr_won),
                        style = BongBaekTheme.typography.body2Regular16,
                        color = BongBaekTheme.colors.txtDisplayPrimary,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }

                EditFieldItem(
                    iconRes = if (isFromResult) ic_record_check_off else ic_record_check_on,
                    labelRes = edit_is_attend_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_is_attend_dropdown_placeholder),
                            menuItems = attendOptions,
                            selectedItem = uiState.attendLabel,
                            onItemSelected = onAttendSelect,
                            isEditable = !isFromResult,
                        )
                    },
                )

                LabelTextField(
                    labelImage = if (isFromResult) ic_record_calendar_off else ic_record_calendar_on,
                    labelName = stringResource(id = edit_date_title),
                    text = uiState.eventDate,
                    placeholder = stringResource(id = edit_date_text_field_placeholder),
                    modifier = Modifier.noRippleClickable {
                        if (!isFromResult) {
                            dialogText =
                                DateFormatter.formatLocalDateToNumeric(uiState.eventDate)
                            isDatePickerDialogVisible = true
                        }
                    },
                    isRequired = true,
                    isEditable = false,
                    isDimmed = isFromResult,
                    isClearButtonEnabled = false,
                    visualTransformation = DateTextFieldFormat(),
                )

                EditFieldItem(
                    iconRes = if (isFromResult) ic_record_location_off else ic_record_location_on,
                    labelRes = edit_location_title,
                    isDimmed = isFromResult,
                    content = uiState.selectedPlace?.let { place ->
                        {
                            EditLocationContent(
                                place = place,
                            )
                        }
                    },
                    isRequired = false,
                    trailing = {
                        Text(
                            text = stringResource(uiState.selectedPlace?.let {
                                edit_location_edit_text
                            } ?: edit_location_add_text),
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.txtDisplayTertiary,
                            modifier = Modifier.noRippleClickable {
                                if (!isFromResult) {
                                    navigateToLocation()
                                }
                            },
                        )
                    },
                )
            }

            if (!isFromResult) {
                EditMemoContent(
                    text = uiState.note,
                    onTextChange = onNoteChange,
                    modifier = Modifier.padding(
                        top = 20.dp,
                    ),
                )
            }

            BongBaekButton(
                title = stringResource(id = edit_save_button),
                onClick = onSubmitEventButtonClick,
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 60.dp,
                        bottom = 36.dp,
                    ),
                enabled = checkIsFormFilled(),
            )
        }
        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.DATE,
                value = dialogText,
                onValueChange = { dialogText = it },
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    dialogText = ""
                },
                onConfirmClick = onDateChange,
            )
        }
    }
}

@Preview
@Composable
private fun EditMainScreenPreview() {
    BongBaekTheme {
        EditMainScreen(
            editEntryType = EditEntryType.FROM_DETAIL,
            uiState = EditUiState(),
            isFromResult = false,
            navigateUp = {},
            navigateToLocation = {},
            onNameChange = {},
            onNicknameChange = {},
            onRelationSelect = {},
            onEventSelect = {},
            onCostChange = {},
            onAttendSelect = {},
            onDateChange = {},
            onNoteChange = {},
            checkIsFormFilled = { true },
            onSubmitEventButtonClick = {},
        )
    }
}
