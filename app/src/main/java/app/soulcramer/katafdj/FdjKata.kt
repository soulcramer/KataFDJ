package app.soulcramer.katafdj

import android.app.Application
import app.soulcramer.cache.cacheModule
import app.soulcramer.presentation.presentationModule
import app.soulcramer.remote.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import timber.log.Timber

class FdjKata : Application() {

    override fun onCreate() {
        super.onCreate()

        // this check is for RoboElectric tests that run in parallel so Koin gets set up multiple times
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(this@FdjKata)
                modules(listOf(remoteModule, cacheModule, presentationModule))
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}