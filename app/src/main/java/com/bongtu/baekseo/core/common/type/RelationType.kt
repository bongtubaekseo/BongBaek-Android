package com.bongtu.baekseo.core.common.type

enum class RelationType(
    val label: String,
) {
    FAMILY(label = "가족/친척"),
    FRIEND(label = "친구"),
    COWORKER(label = "직장"),
    ALUMNI(label = "선후배"),
    NEIGHBOR(label = "이웃"),
    ETC(label = "기타");
}
