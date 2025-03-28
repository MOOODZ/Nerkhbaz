package ir.moodz.sarafkoochooloo.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Currencies: Destination

    @Serializable
    data object Convert: Destination
}