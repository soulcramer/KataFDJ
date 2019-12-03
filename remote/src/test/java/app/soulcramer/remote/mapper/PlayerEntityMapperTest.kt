package app.soulcramer.remote.mapper

import app.soulcramer.remote.test.RemotePlayerFactory
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class PlayerEntityMapperTest {

    private lateinit var playerEntityMapper: PlayerEntityMapper

    @Before
    fun setUp() {
        playerEntityMapper = PlayerEntityMapper()
    }

    @Test
    fun `Given a remote League When mapping it Then return a league with correct info`() {
        val remoteLeague = RemotePlayerFactory.makePlayer()
        val leagueEntity = playerEntityMapper.mapFromRemote(remoteLeague)

        assertThat(leagueEntity.id).isEqualTo(remoteLeague.idTeam)
        assertThat(leagueEntity.fullname).isEqualTo(remoteLeague.strPlayer)
        assertThat(leagueEntity.position).isEqualTo(remoteLeague.strPosition)
        assertThat(leagueEntity.signin).isEqualTo(remoteLeague.strSigning)
        assertThat(leagueEntity.birthday).isEqualTo(remoteLeague.dateBorn)
        assertThat(leagueEntity.imageUrl).isEqualTo(remoteLeague.strCutout)
    }
}