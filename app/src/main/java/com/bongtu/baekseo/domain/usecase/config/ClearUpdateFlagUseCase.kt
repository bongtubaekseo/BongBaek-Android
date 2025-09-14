package com.bongtu.baekseo.domain.usecase.config

import com.bongtu.baekseo.core.local.datastore.ConfigDataStore
import javax.inject.Inject

// 추후 선택 업데이트 추가 시 필요
class ClearUpdateFlagUseCase @Inject constructor(
    private val configDataStore: ConfigDataStore,
) {
    suspend operator fun invoke() = configDataStore.clearUpdateFlag()
}
