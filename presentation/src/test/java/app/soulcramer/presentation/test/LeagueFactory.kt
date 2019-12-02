package app.soulcramer.presentation.test

import app.soulcramer.domain.model.League
import app.soulcramer.presentation.test.DataFactory.Factory.randomUuid

/**
 * Factory class for League related instances
 */
class LeagueFactory {

    companion object Factory {

        fun makeLeague(
            id: String = randomUuid(),
            name: String = randomUuid(),
            sport: String = randomUuid()
        ): League {
            return League(id, name, sport)
        }

        fun makeLeagueList(
            count: Int,
            name: String = "",
            hasOtherSportThanSoccer: Boolean = false
        ): List<League> {
            val teams = mutableListOf<League>()
            repeat(count) {
                val sport = DataFactory.makeSport(hasOtherSportThanSoccer)
                teams += makeLeague(name = name, sport = sport)
            }
            return teams
        }
    }
}