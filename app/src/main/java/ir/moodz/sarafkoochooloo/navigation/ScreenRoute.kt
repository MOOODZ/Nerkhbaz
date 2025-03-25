package ir.moodz.sarafkoochooloo.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object Main: ScreenRoute

    @Serializable
    data class Detail(val id: Int): ScreenRoute

}