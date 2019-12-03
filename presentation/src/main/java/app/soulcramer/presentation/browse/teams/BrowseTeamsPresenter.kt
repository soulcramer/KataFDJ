package app.soulcramer.presentation.browse.teams

import app.soulcramer.domain.interactor.GetAllLeagues
import app.soulcramer.domain.interactor.GetLeagueTeams
import app.soulcramer.domain.interactor.invoke
import app.soulcramer.presentation.mapper.LeagueMapper
import app.soulcramer.presentation.mapper.TeamMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BrowseTeamsPresenter(
    var browseView: BrowseTeamsContract.View?,
    val getAllLeagues: GetAllLeagues,
    val getLeagueTeams: GetLeagueTeams,
    val leagueMapper: LeagueMapper,
    val teamMapper: TeamMapper,
    val coroutineScope: CoroutineScope,
    val leagueName: String
) : BrowseTeamsContract.Presenter {

    private var debounceTeamSearchJob: Job? = null

    override fun start() {
        coroutineScope.launch {
            retrieveLeagues()
        }
        coroutineScope.launch {
            retrieveTeams("")
        }
    }

    override fun stop() {
        browseView = null
    }

    override fun retrieveTeams(leagueName: String) {
        debounceTeamSearchJob?.cancel()
        debounceTeamSearchJob = coroutineScope.launch {
            delay(TimeUnit.MILLISECONDS.toMillis(500))
            browseView?.showProgress()
            val teams = getLeagueTeams(leagueName).map(teamMapper::mapToView)
            browseView?.hideProgress()
            if (teams.isEmpty()) {
                browseView?.hideTeams()
                browseView?.showEmptyState()
            } else {
                browseView?.hideEmptyState()
                browseView?.showTeams(teams)
            }
        }
    }

    override suspend fun retrieveLeagues() {
        val leagues = getAllLeagues().map(leagueMapper::mapToView)
        browseView?.loadLeagues(leagues)
    }
}