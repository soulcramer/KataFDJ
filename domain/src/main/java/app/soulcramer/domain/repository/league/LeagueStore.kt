package app.soulcramer.domain.repository.league

import app.soulcramer.domain.model.League

interface LeagueStore {
    suspend fun getAllLeagues(): List<League>
}