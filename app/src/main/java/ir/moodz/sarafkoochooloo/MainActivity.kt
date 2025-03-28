package ir.moodz.sarafkoochooloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.moodz.sarafkoochooloo.navigation.Destination
import ir.moodz.sarafkoochooloo.navigation.NavigationAction
import ir.moodz.sarafkoochooloo.navigation.Navigator
import ir.moodz.sarafkoochooloo.presentation.currency.CurrencyRoot
import ir.moodz.sarafkoochooloo.presentation.util.ObserveAsEvents
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NerkhbazTheme {
                val navController = rememberNavController()
                val navigator = koinInject<Navigator>()

                ObserveAsEvents(flow = navigator.navigationActions) { action ->
                    when(action) {
                        is NavigationAction.Navigate -> navController.navigate(action.destination) {
                            action.navOptions(this)
                        }
                        NavigationAction.NavigateUp -> navController.navigateUp()
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = Destination.Currencies
                ) {
                    composable<Destination.Currencies> {
                        CurrencyRoot()
                    }
                }
            }
        }
    }
}
