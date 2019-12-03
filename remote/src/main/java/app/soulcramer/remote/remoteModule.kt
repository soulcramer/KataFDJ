package app.soulcramer.remote

import app.soulcramer.domain.repository.league.LeagueRemoteStore
import app.soulcramer.domain.repository.team.TeamRemoteStore
import app.soulcramer.remote.mapper.LeagueEntityMapper
import app.soulcramer.remote.mapper.PlayerEntityMapper
import app.soulcramer.remote.mapper.TeamEntityMapper
import org.koin.core.module.Module
import org.koin.dsl.module

val remoteModule: Module = module(override = true) {

    factory { LeagueEntityMapper() }
    factory { TeamEntityMapper() }
    factory { PlayerEntityMapper() }

    single {
        FdjServiceFactory.makeFdjService(BuildConfig.DEBUG)
    }

    factory<LeagueRemoteStore> { LeagueRemoteImpl(get(), get(), get()) }
    factory<TeamRemoteStore> { TeamRemoteImpl(get(), get()) }
}