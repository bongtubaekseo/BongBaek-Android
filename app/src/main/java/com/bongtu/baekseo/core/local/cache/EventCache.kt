package com.bongtu.baekseo.core.local.cache

import com.bongtu.baekseo.data.model.event.EditEvent

object EventCache {
    private var cachedEvent: EditEvent? = null

    fun save(event: EditEvent) {
        cachedEvent = event
    }

    fun load(): EditEvent? = cachedEvent

    fun clear() {
        cachedEvent = null
    }
}
