package ir.moodz.sarafkoochooloo.di

import androidx.room.Room
import ir.moodz.sarafkoochooloo.data.local.MIGRATION_1_2
import ir.moodz.sarafkoochooloo.data.local.NerkhbazDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            NerkhbazDatabase::class.java,
            "nerkhbaz_db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    single { get<NerkhbazDatabase>().currencyDao }

}