package ir.moodz.sarafkoochooloo

import android.app.Application
import ir.moodz.sarafkoochooloo.di.appModule
import ir.moodz.sarafkoochooloo.di.dataSourceModule
import ir.moodz.sarafkoochooloo.di.databaseModule
import ir.moodz.sarafkoochooloo.di.repositoryModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class NerkhbazApp : Application() {

    val applicationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@NerkhbazApp)
            modules(
                appModule,
                dataSourceModule,
                repositoryModule,
                databaseModule
            )
        }
    }
}