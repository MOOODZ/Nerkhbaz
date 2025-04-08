package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.network.model.CheckUpdateResponseDto
import ir.moodz.sarafkoochooloo.domain.model.CheckUpdate

fun CheckUpdateResponseDto.asDomain(): CheckUpdate {
    return CheckUpdate(
        isUpdatedNeeded = isUpdateAvailable,
        updateUrl = urlUpdate
    )
}