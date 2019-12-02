package app.soulcramer.domain.repository.league

import app.soulcramer.domain.model.Team

interface LeagueRemoteStore : LeagueStore {
    suspend fun getTeams(leagueName: String): List<Team>
}