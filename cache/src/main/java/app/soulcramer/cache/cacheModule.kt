package app.soulcramer.cache

import android.os.Debug
import androidx.room.Room
import app.soulcramer.cache.mapper.LeagueEntityMapper
import app.soulcramer.domain.repository.league.LeagueCacheStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val cacheModule: Module = module(override = true) {
    factory { PreferencesHelper(androidContext()) }

    single {
        val builder = Room.databaseBuilder(androidContext(), FdjDatabase::class.java, "fdj.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return@single builder.build()
    }

    single { get<FdjDatabase>().leagueDao() }

    factory { LeagueEntityMapper() }

    factory<LeagueCacheStore> { LeagueCacheStoreImpl(get(), get(), get()) }
}