package app.soulcramer.cache.mapper

import app.soulcramer.cache.model.CachedLeague
import app.soulcramer.cache.test.CachedLeagueFactory
import app.soulcramer.cache.test.DataFactory
import app.soulcramer.domain.model.League
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class LeagueEntityMapperTest {

    private lateinit var leagueEntityMapper: LeagueEntityMapper

    @Before
    fun setUp() {
        leagueEntityMapper = LeagueEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val leagueEntity = makeLeague()
        val cachedLeague = leagueEntityMapper.mapToCached(leagueEntity)

        assertUserDataEquality(leagueEntity, cachedLeague)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedLeague = CachedLeagueFactory.makeLeague()
        val leagueEntity = leagueEntityMapper.mapFromCached(cachedLeague)

        assertUserDataEquality(leagueEntity, cachedLeague)
    }

    private fun assertUserDataEquality(leagueEntity: League, cachedLeague: CachedLeague) {
        assertThat(leagueEntity.id).isEqualTo(cachedLeague.id)
        assertThat(leagueEntity.name).isEqualTo(cachedLeague.name)
        assertThat(leagueEntity.sport).isEqualTo(cachedLeague.sport)
    }

    private fun makeLeague(
        id: String = DataFactory.randomUuid(),
        name: String = DataFactory.randomUuid(),
        sport: String = DataFactory.randomUuid()
    ): League {
        return League(id, name, sport)
    }
}