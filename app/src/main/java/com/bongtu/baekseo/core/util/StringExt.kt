package com.bongtu.baekseo.core.util

import java.text.BreakIterator

fun String.checkLength(): Int {
    val iterator = BreakIterator.getCharacterInstance()
    iterator.setText(this)

    var count = 0
    while (iterator.next() != BreakIterator.DONE)
        count++

    return count
}