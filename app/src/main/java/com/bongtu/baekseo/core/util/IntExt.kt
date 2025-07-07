package com.bongtu.baekseo.core.util

/**
 * 숫자에 천 단위 쉼표(,)를 추가하여 금액 형식의 문자열로 변환 함수
 */
fun Int.toFormattedCost(): String = "%,d".format(this)