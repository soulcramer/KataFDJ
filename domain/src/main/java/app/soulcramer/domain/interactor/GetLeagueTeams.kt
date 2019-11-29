package app.soulcramer.domain.interactor

import app.soulcramer.domain.model.Team
import app.soulcramer.domain.repository.LeagueRepository

class GetLeagueTeams(private val leagueRepository: LeagueRepository) :
    UseCase<String, List<Team>>() {

    override suspend fun doWork(params: String): List<Team> {
        return leagueRepository.getTeams(params)
    }
}