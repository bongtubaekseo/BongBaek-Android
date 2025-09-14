package com.bongtu.baekseo.presentation.edit

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_arrow_down
import com.bongtu.baekseo.R.drawable.ic_arrow_up
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_check_gray
import com.bongtu.baekseo.R.drawable.ic_coin
import com.bongtu.baekseo.R.drawable.ic_event
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.drawable.ic_relation
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
import com.bongtu.baekseo.R.string.edit_required_text
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
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.CostTextFieldFormat
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.clearFocus
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect.NavigateToFinal
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect.NavigateToLocation
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect.NavigateToRecord
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect.NavigateToSchedule
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import com.bongtu.baekseo.presentation.edit.component.EditDropdownMenu
import com.bongtu.baekseo.presentation.edit.component.EditLocationContent
import com.bongtu.baekseo.presentation.edit.component.EditMemoContent
import com.bongtu.baekseo.presentation.edit.component.EditFieldItem
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.filterIsInstance

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
            .filterIsInstance<EditSideEffect.EditMainSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToRecord -> navigateUp()
                    is NavigateToDetail -> navigateUp()
                    is NavigateToSchedule -> navigateUp()
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
    var text by remember { mutableStateOf("") }
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    val relations = RelationType.entries.map { it.label }.toImmutableList()
    val events = EventType.entries.map { it.label }.toImmutableList()
    val attendOptions = AttendType.entries.map { it.label }.toImmutableList()

    val isFromResult = remember(editEntryType) {
        editEntryType == EditEntryType.FROM_RESULT
    }

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
                color = BongBaekTheme.colors.gray900,
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
                    tint = BongBaekTheme.colors.white,
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
                        color = BongBaekTheme.colors.gray800,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(vertical = 24.dp, horizontal = 20.dp),
            ) {
                LabelTextField(
                    labelName = stringResource(id = edit_name_title),
                    labelImage = ic_person,
                    text = uiState.name,
                    placeholder = stringResource(id = edit_name_text_field_placeholder),
                    modifier = Modifier.padding(bottom = 32.dp),
                    errorText = uiState.nameError,
                    onTextChange = onNameChange,
                    isRequired = true,
                    isEditable = !isFromResult,
                    isDimmed = isFromResult,
                )

                LabelTextField(
                    labelName = stringResource(id = edit_nickname_title),
                    labelImage = ic_nickname,
                    text = uiState.nickname,
                    placeholder = stringResource(id = edit_nickname_text_field_placeholder),
                    modifier = Modifier.padding(bottom = 32.dp),
                    errorText = uiState.nicknameError,
                    onTextChange = onNicknameChange,
                    isRequired = true,
                    isEditable = !isFromResult,
                    isDimmed = isFromResult,
                )

                EditFieldItem(
                    iconRes = ic_relation,
                    labelRes = edit_relation_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_relation_dropdown_placeholder),
                            menuItems = relations,
                            selectedItem = uiState.relationship,
                            onItemSelected = onRelationSelect,
                            isEditable = !isFromResult,
                            isDimmed = isFromResult,
                        )
                    },
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                EditFieldItem(
                    iconRes = ic_event,
                    labelRes = edit_event_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_event_dropdown_placeholder),
                            menuItems = events,
                            selectedItem = uiState.eventCategory,
                            onItemSelected = onEventSelect,
                            isEditable = !isFromResult,
                            isDimmed = isFromResult,
                        )
                    },
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    LabelTextField(
                        labelName = stringResource(id = edit_cost_title),
                        labelImage = ic_coin,
                        text = uiState.cost,
                        modifier = Modifier.weight(1f),
                        placeholder = stringResource(id = edit_cost_text_field_placeholder),
                        errorText = uiState.costError,
                        onTextChange = onCostChange,
                        isRequired = true,
                        keyboardType = KeyboardType.NumberPassword,
                        visualTransformation = CostTextFieldFormat(),
                        bottomSpacer = 32.dp,
                    )

                    Text(
                        text = stringResource(kr_won),
                        style = BongBaekTheme.typography.body2Regular16,
                        color = BongBaekTheme.colors.white,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 33.dp,
                            ),
                    )
                }

                EditFieldItem(
                    iconRes = ic_check_gray,
                    labelRes = edit_is_attend_title,
                    isDimmed = isFromResult,
                    content = {
                        EditDropdownMenu(
                            placeholder = stringResource(id = edit_is_attend_dropdown_placeholder),
                            menuItems = attendOptions,
                            selectedItem = uiState.attendLabel,
                            onItemSelected = onAttendSelect,
                            isEditable = !isFromResult,
                            isDimmed = isFromResult,
                        )
                    },
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = edit_date_title),
                    text = uiState.eventDate,
                    placeholder = stringResource(id = edit_date_text_field_placeholder),
                    modifier = Modifier
                        .noRippleClickable {
                            text = DateFormatter.formatLocalDateToNumeric(uiState.eventDate)
                            if (!isFromResult) isDatePickerDialogVisible = true
                        }
                        .padding(bottom = 32.dp),
                    isRequired = true,
                    isEditable = false,
                    isDimmed = isFromResult,
                    isClearButtonEnabled = false,
                    visualTransformation = DateTextFieldFormat(),
                )

                EditFieldItem(
                    iconRes = ic_location,
                    labelRes = edit_location_title,
                    isDimmed = isFromResult,
                    content = {
                        if (isFromResult) {
                            Text(
                                text = stringResource(id = location_empty_text),
                                style = BongBaekTheme.typography.body1Medium14,
                                color = BongBaekTheme.colors.gray400,
                            )
                        } else {
                            uiState.selectedPlace?.let { place ->
                                EditLocationContent(
                                    place = place,
                                )
                            }
                        }
                    },
                    isRequired = false,
                    trailing = {
                        if (!isFromResult) {
                            Text(
                                text = stringResource(
                                    uiState.selectedPlace?.let {
                                        edit_location_edit_text
                                    } ?: edit_location_add_text
                                ),
                                style = BongBaekTheme.typography.body2Regular14,
                                color = BongBaekTheme.colors.gray300,
                                modifier = Modifier
                                    .noRippleClickable { navigateToLocation() },
                            )
                        }
                    },
                )
            }

            EditMemoContent(
                text = uiState.note,
                onTextChange = onNoteChange,
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                    ),
                isEditable = isResultEditable,
            )

            BongBaekButton(
                title = stringResource(id = edit_save_button),
                onClick = onSubmitEventButtonClick,
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 40.dp,
                        bottom = 36.dp,
                    ),
                enabled = checkIsFormFilled(),
            )
        }
        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.DATE,
                value = text,
                onValueChange = { text = it },
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    text = ""
                },
                onConfirmClick = onDateChange,
            )
        }
    }
}

@Preview
@Composable
private fun FormFieldItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    isDimmed: Boolean = false,
    trailing: (@Composable () -> Unit)? = null,
) {
    val textColor = if (isDimmed) BongBaekTheme.colors.gray400 else BongBaekTheme.colors.white

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
                color = textColor,
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
    isEditable: Boolean = true,
    isDimmed: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    val isSelected = selectedItem.isNotBlank()
    val bongBaekColors = BongBaekTheme.colors
    val text = if (isSelected) selectedItem else placeholder
    val textColor = when {
        isDimmed -> bongBaekColors.gray400
        isSelected && expanded -> bongBaekColors.primaryNormal
        isSelected || expanded -> bongBaekColors.white
        else -> bongBaekColors.gray300
    }
    val borderColor =
        if (expanded) BongBaekTheme.colors.primaryNormal else BongBaekTheme.colors.transparent
    var rowWidthPx by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .onGloballyPositioned { coordinates ->
                rowWidthPx = coordinates.size.width
            }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            )
            .noRippleClickable {
                if (isEditable && !expanded) expanded = true
            },
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
private fun EditMainScreenPreview() {
    BongBaekTheme {
        EditMainScreen(
            editEntryType = EditEntryType.FROM_DETAIL,
            uiState = EditUiState(),
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

    BongBaekDropdownMenu(
        expanded = expanded,
        items = menuItems,
        selectedItem = selectedItem,
        onDismissRequest = { expanded = false },
        onItemSelect = { onItemSelected(it) },
        label = { it },
        modifier = Modifier
            .width(with(LocalDensity.current) { rowWidthPx.toDp() }),
        maxItemSize = menuItems.size,
        isFocusable = true,
    )
}
