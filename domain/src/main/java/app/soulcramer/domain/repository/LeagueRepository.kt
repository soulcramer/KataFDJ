package app.soulcramer.domain.repository

import app.soulcramer.domain.model.Team

interface LeagueRepository {

    fun getTeams(leagueName: String): List<Team>

    fun getAllLeagues(): List<Team>
}