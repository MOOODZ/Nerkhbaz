package ir.moodz.sarafkoochooloo.di

import ir.moodz.sarafkoochooloo.data.repository.OfflineFirstCurrencyRepository
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::OfflineFirstCurrencyRepository).bind<CurrenciesRepository>()
}