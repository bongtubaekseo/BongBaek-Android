package com.bongtu.baekseo.data.repository.event

import com.bongtu.baekseo.data.model.event.Cost
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location

interface EventRepository {
    suspend fun postEventInfo(
        host: Host,
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Unit>

    suspend fun postEventCost(
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Cost>
}
