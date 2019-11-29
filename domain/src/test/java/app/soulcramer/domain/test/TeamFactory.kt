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
            imageUrl: String = randomUuid()
        ): Team {
            return Team(id, name, imageUrl)
        }

        fun makeTeamList(
            count: Int,
            name: String = ""
        ): List<Team> {
            val teams = mutableListOf<Team>()
            repeat(count) {
                teams += makeTeam()
            }
            return teams
        }
    }
}