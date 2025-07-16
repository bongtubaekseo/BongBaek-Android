package com.bongtu.baekseo.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat

class CostTextFieldFormat : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        val clean = raw.replace(",", "")

        if (clean.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val number = clean.toLongOrNull()?.coerceIn(0L, 99_999_999L) ?: 0L
        val formatted = DecimalFormat("#,###").format(number)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = offset
                var digitsProcessed = 0
                for (i in formatted.indices) {
                    if (formatted[i].isDigit()) {
                        if (digitsProcessed == offset) break
                        digitsProcessed++
                    }
                    transformedOffset++
                }
                return transformedOffset.coerceAtMost(formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var digitsSeen = 0
                for (i in 0 until offset.coerceAtMost(formatted.length)) {
                    if (formatted[i].isDigit()) digitsSeen++
                }
                return digitsSeen.coerceAtMost(clean.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

