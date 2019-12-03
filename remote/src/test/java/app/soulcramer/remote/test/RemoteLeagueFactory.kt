package app.soulcramer.remote.test

import app.soulcramer.remote.model.RemoteLeague
import app.soulcramer.remote.test.DataFactory.Factory.randomUuid

class RemoteLeagueFactory {

    companion object Factory {

        fun makeLeague(
            id: String = randomUuid(),
            name: String = randomUuid(),
            sport: String = randomUuid()
        ): RemoteLeague {
            return RemoteLeague(id, name, sport)
        }

        fun makeLeagueList(
            count: Int,
            name: String = "",
            hasOtherSportThanSoccer: Boolean = false
        ): List<RemoteLeague> {
            val teams = mutableListOf<RemoteLeague>()
            repeat(count) {
                val sport = DataFactory.makeSport(hasOtherSportThanSoccer)
                teams += makeLeague(name = name, sport = sport)
            }
            return teams
        }
    }
}