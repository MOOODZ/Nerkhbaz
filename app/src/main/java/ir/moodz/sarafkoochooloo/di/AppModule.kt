package ir.moodz.sarafkoochooloo.di

import ir.moodz.sarafkoochooloo.NerkhbazApp
import ir.moodz.sarafkoochooloo.data.network.HttpClientFactory
import ir.moodz.sarafkoochooloo.presentation.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory().build()
    }
    single<CoroutineScope> {
        (androidApplication() as NerkhbazApp).applicationScope
    }

    viewModelOf(::MainViewModel)
}