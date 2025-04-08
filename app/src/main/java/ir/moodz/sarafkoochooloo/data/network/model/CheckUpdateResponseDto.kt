package ir.moodz.sarafkoochooloo.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckUpdateDataDto(
    val data: CheckUpdateWrapperDto,
    val response: ApiResponseDetailDto
)

@Serializable
data class CheckUpdateWrapperDto(
    val update: CheckUpdateResponseDto
)

@Serializable
data class CheckUpdateResponseDto(
    @SerialName("has_update")
    val isUpdateAvailable: Boolean,
    @SerialName("update_url")
    val urlUpdate: String,
    @SerialName("force_update")
    val isForced: Boolean
)
