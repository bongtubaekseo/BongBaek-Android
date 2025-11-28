package com.bongtu.baekseo.presentation.edit.type

enum class EditEntryType(
    val editType: EditType,
) {
    FROM_RECORD(editType = EditType.ADD),
    FROM_DETAIL(editType = EditType.EDIT),
    FROM_RESULT(editType = EditType.EDIT),
}
