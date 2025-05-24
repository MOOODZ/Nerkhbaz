package ir.moodz.sarafkoochooloo.di

import ir.moodz.sarafkoochooloo.NerkhbazApp
import ir.moodz.sarafkoochooloo.data.network.HttpClientFactory
import ir.moodz.sarafkoochooloo.navigation.DefaultNavigator
import ir.moodz.sarafkoochooloo.navigation.Destination
import ir.moodz.sarafkoochooloo.navigation.Navigator
import ir.moodz.sarafkoochooloo.presentation.currency.CurrencyViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.build() }
    single<CoroutineScope> {
        (androidApplication() as NerkhbazApp).applicationScope
    }
    single<Navigator> {
        DefaultNavigator(startDestination = Destination.Currencies)
    }

    viewModelOf(::CurrencyViewModel)
    single {

    }
}