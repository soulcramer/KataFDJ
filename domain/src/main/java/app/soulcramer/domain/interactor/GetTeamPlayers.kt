package app.soulcramer.domain.interactor

import app.soulcramer.domain.model.Player
import app.soulcramer.domain.repository.team.TeamRemoteStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetTeamPlayers(
    private val teamRemoteStore: TeamRemoteStore,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UseCase<String, List<Player>>(dispatcher) {

    override suspend fun doWork(params: String): List<Player> {
        return teamRemoteStore.getPlayers(params)
    }
}