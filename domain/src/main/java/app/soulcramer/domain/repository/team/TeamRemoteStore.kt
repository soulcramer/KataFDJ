package app.soulcramer.domain.repository.team

import app.soulcramer.domain.model.Player

interface TeamRemoteStore {
    fun getPlayers(teamName: String): List<Player>
}