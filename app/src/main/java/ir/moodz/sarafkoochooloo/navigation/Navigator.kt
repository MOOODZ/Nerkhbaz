package ir.moodz.sarafkoochooloo.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface Navigator {

    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

