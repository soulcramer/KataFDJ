package app.soulcramer.domain.interactor

import app.soulcramer.domain.model.Player

class GetTeamPlayers() : UseCase<String, List<Player>>() {

    override suspend fun doWork(params: String): List<Player> {

        return emptyList()
    }
}