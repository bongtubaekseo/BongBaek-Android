package com.bongtu.baekseo.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Date picker format
 *
 * Date picker에서 사용하는 날짜 입력 포맷
 * in: "yyyyMMdd"
 * out: "yyyy년 MM월 dd일"
 */
class DatePickerFormat : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        val out = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 3) append("년 ")
                if (i == 5) append("월 ")
                if (i == 7) append("일")
            }
        }

        return TransformedText(AnnotatedString(out), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 5 -> offset + 2
                    offset <= 7 -> offset + 4
                    else -> 13
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 8 -> offset - 2
                    offset <= 12 -> offset - 4
                    else -> 8
                }
            }
        })
    }
}