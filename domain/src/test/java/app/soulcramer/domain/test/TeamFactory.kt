package app.soulcramer.domain.test

import app.soulcramer.domain.model.Team
import app.soulcramer.domain.test.DataFactory.Factory.randomUuid

/**
 * Factory class for Team related instances
 */
class TeamFactory {

    companion object Factory {

        fun makeTeam(
            id: String = randomUuid(),
            name: String = randomUuid(),
            sport: String = randomUuid(),
            imageUrl: String = randomUuid()
        ): Team {
            return Team(id, name, sport, imageUrl)
        }

        fun makeTeamList(
            count: Int,
            name: String = "",
            hasOtherSportThanSoccer: Boolean = false
        ): List<Team> {
            val teams = mutableListOf<Team>()
            repeat(count) {
                val sport = DataFactory.makeSport(hasOtherSportThanSoccer)
                teams += makeTeam(name = name, sport = sport)
            }
            return teams
        }
    }
}