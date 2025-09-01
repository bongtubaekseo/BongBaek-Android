package com.bongtu.baekseo.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTextFieldFormat : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text

        if (input.length < 8) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val year = input.substring(0, 4).toIntOrNull()
        val month = input.substring(5, 7).toIntOrNull()
        val day = input.substring(8, 10).toIntOrNull()

        val formatted = buildString {
            if (year != null) append("${year}년 ")
            if (month != null) append("${month}월 ")
            if (day != null) append("${day}일")
        }

        return TransformedText(AnnotatedString(formatted), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return input.length.coerceAtMost(8)
            }
        })
    }
}