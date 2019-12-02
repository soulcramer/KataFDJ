package app.soulcramer.domain.interactor

import app.soulcramer.domain.model.Team
import app.soulcramer.domain.repository.league.LeagueRemoteStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetLeagueTeams(
    private val leagueRemoteStore: LeagueRemoteStore,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    UseCase<String, List<Team>>(dispatcher) {

    override suspend fun doWork(params: String): List<Team> {
        return leagueRemoteStore.getTeams(params).filter {
            it.sport == "Soccer"
        }.sortedBy {
            it.name
        }
    }
}