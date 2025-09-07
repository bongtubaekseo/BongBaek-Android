package com.bongtu.baekseo.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTextFieldFormat : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text

        if (input.length < 10) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val formatted = DateFormatter.formatToKorean(input)

        return TransformedText(AnnotatedString(formatted), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return input.length
            }
        })
    }
}