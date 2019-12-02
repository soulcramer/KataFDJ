package app.soulcramer.domain.interactor

import app.soulcramer.domain.model.League
import app.soulcramer.domain.repository.league.LeagueStoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetAllLeagues(
    private val leagueStoreFactory: LeagueStoreFactory,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UseCase<Unit, List<League>>(dispatcher) {
    override suspend fun doWork(params: Unit): List<League> {
        return leagueStoreFactory.retrieveStore().getAllLeagues().filter {
            it.sport == "Soccer"
        }.sortedBy {
            it.name
        }
    }
}