package ir.moodz.sarafkoochooloo.di

import ir.moodz.sarafkoochooloo.data.local.RoomLocalDataSource
import ir.moodz.sarafkoochooloo.data.network.KtorRemoteDataSource
import ir.moodz.sarafkoochooloo.domain.local.LocalDataSource
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    singleOf(::RoomLocalDataSource).bind<LocalDataSource>()
}