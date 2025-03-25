package ir.moodz.sarafkoochooloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.moodz.sarafkoochooloo.navigation.ScreenRoute
import ir.moodz.sarafkoochooloo.presentation.main.MainRoot
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NerkhbazTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.Main
                ) {
                    composable<ScreenRoute.Main> {
                        MainRoot()
                    }
                }
            }
        }
    }
}
