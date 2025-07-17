package com.bongtu.baekseo.core.local.cache

import com.bongtu.baekseo.data.model.event.CachingEvent


object EventCache {
    private var cachedEvent: CachingEvent? = null

    fun save(event: CachingEvent) {
        cachedEvent = event
    }

    fun load(): CachingEvent? = cachedEvent

    fun clear() {
        cachedEvent = null
    }
}
