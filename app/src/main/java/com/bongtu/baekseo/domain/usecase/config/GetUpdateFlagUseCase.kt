package com.bongtu.baekseo.domain.usecase.config

import com.bongtu.baekseo.core.local.datastore.ConfigDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpdateFlagUseCase @Inject constructor(
    private val configDataStore: ConfigDataStore,
) {
    operator fun invoke(): Flow<Boolean> = configDataStore.getUpdateFlag()
}
