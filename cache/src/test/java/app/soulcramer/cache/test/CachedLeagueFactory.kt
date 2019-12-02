package app.soulcramer.cache.test

import app.soulcramer.cache.model.CachedLeague
import app.soulcramer.cache.test.DataFactory.Factory.randomUuid

class CachedLeagueFactory {

    companion object Factory {

        fun makeLeague(
            id: String = randomUuid(),
            name: String = randomUuid(),
            sport: String = randomUuid()
        ): CachedLeague {
            return CachedLeague(id, name, sport)
        }

        fun makeLeagueList(
            count: Int,
            name: String = "",
            hasOtherSportThanSoccer: Boolean = false
        ): List<CachedLeague> {
            val teams = mutableListOf<CachedLeague>()
            repeat(count) {
                val sport = DataFactory.makeSport(hasOtherSportThanSoccer)
                teams += makeLeague(name = name, sport = sport)
            }
            return teams
        }
    }
}