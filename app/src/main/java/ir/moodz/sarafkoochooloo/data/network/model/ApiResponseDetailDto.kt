package ir.moodz.sarafkoochooloo.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDetailDto(
    @SerialName("status") val statusCode: Int,
    @SerialName("show_dialog") val shouldShowDialog: Boolean,
    @SerialName("message") val responseMessage: String,
    @SerialName("positive_btn") val positiveButtonText: String,
    @SerialName("negative_btn") val negativeButtonText: String,
    @SerialName("positive_url") val positiveButtonUrl: String,
    @SerialName("can_dismiss") val isDismissible: Boolean
)