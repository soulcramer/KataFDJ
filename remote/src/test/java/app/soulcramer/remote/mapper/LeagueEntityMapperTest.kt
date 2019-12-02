package app.soulcramer.remote.mapper

import app.soulcramer.remote.test.RemoteLeagueFactory
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
    fun `Given a remote League When mapping it Then return a league with correct info`() {
        val remoteLeague = RemoteLeagueFactory.makeLeague()
        val leagueEntity = leagueEntityMapper.mapFromRemote(remoteLeague)

        assertThat(leagueEntity.id).isEqualTo(remoteLeague.idLeague)
        assertThat(leagueEntity.name).isEqualTo(remoteLeague.strLeague)
        assertThat(leagueEntity.sport).isEqualTo(remoteLeague.strSport)
    }
}