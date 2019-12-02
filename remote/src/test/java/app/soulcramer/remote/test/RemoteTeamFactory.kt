package app.soulcramer.remote.test

import app.soulcramer.remote.model.RemoteTeam
import app.soulcramer.remote.test.DataFactory.Factory.randomUuid

class RemoteTeamFactory {

    companion object Factory {

        fun makeTeam(
            id: String = randomUuid(),
            name: String = randomUuid(),
            sport: String = randomUuid(),
            imageUrl: String = randomUuid()
        ): RemoteTeam {
            return RemoteTeam(id, name, sport, imageUrl)
        }

        fun makeTeamList(
            count: Int,
            name: String = "",
            hasOtherSportThanSoccer: Boolean = false
        ): List<RemoteTeam> {
            val teams = mutableListOf<RemoteTeam>()
            repeat(count) {
                val sport = DataFactory.makeSport(hasOtherSportThanSoccer)
                teams += makeTeam(name = name, sport = sport)
            }
            return teams
        }
    }
}