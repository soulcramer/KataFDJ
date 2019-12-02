package app.soulcramer.remote

import app.soulcramer.domain.model.League
import app.soulcramer.domain.model.Team
import app.soulcramer.domain.repository.league.LeagueRemoteStore
import app.soulcramer.remote.mapper.LeagueEntityMapper
import app.soulcramer.remote.mapper.TeamEntityMapper

class LeagueRemoteImpl(
    private val service: FdjService,
    private val leagueEntityMapper: LeagueEntityMapper,
    private val teamEntityMapper: TeamEntityMapper
) : LeagueRemoteStore {

    override suspend fun getTeams(leagueName: String): List<Team> {
        return service.getLeagueTeams(leagueName).teams?.map(teamEntityMapper::mapFromRemote)
            ?: emptyList()
    }

    override suspend fun getAllLeagues(): List<League> {
        return service.getLeagues().leagues?.map(leagueEntityMapper::mapFromRemote)
            ?: emptyList()
    }
}