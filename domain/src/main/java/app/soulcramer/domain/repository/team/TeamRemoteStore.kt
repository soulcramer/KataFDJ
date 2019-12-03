package app.soulcramer.domain.repository.team

import app.soulcramer.domain.model.Player

interface TeamRemoteStore {
    suspend fun getPlayers(teamName: String): List<Player>
}