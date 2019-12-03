package app.soulcramer.presentation

import app.soulcramer.domain.interactor.GetAllLeagues
import app.soulcramer.domain.interactor.GetLeagueTeams
import app.soulcramer.domain.interactor.GetTeamPlayers
import app.soulcramer.domain.repository.league.LeagueStoreFactory
import app.soulcramer.presentation.browse.players.BrowsePlayersContract
import app.soulcramer.presentation.browse.players.BrowsePlayersPresenter
import app.soulcramer.presentation.browse.teams.BrowseTeamsContract
import app.soulcramer.presentation.browse.teams.BrowseTeamsPresenter
import app.soulcramer.presentation.mapper.LeagueMapper
import app.soulcramer.presentation.mapper.PlayerMapper
import app.soulcramer.presentation.mapper.TeamMapper
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: Module = module(override = true) {

    single { LeagueStoreFactory(get(), get()) }

    factory { GetTeamPlayers(get()) }
    factory { GetAllLeagues(get()) }
    factory { GetLeagueTeams(get()) }

    factory { PlayerMapper() }
    factory { LeagueMapper() }
    factory { TeamMapper() }

    factory<BrowseTeamsContract.Presenter> { (view: BrowseTeamsContract.View, coroutineScope: CoroutineScope, leagueName: String) ->
        BrowseTeamsPresenter(view, get(), get(), get(), get(), coroutineScope, leagueName)
    }

    factory<BrowsePlayersContract.Presenter> { (view: BrowsePlayersContract.View, coroutineScope: CoroutineScope, teamName: String) ->
        BrowsePlayersPresenter(view, get(), get(), coroutineScope, teamName)
    }
}