package app.soulcramer.remote.mapper

import app.soulcramer.remote.test.RemoteTeamFactory
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TeamEntityMapperTest {

    private lateinit var teamEntityMapper: TeamEntityMapper

    @Before
    fun setUp() {
        teamEntityMapper = TeamEntityMapper()
    }

    @Test
    fun `Given a remote League When mapping it Then return a league with correct info`() {
        val remoteLeague = RemoteTeamFactory.makeTeam()
        val leagueEntity = teamEntityMapper.mapFromRemote(remoteLeague)

        assertThat(leagueEntity.id).isEqualTo(remoteLeague.idTeam)
        assertThat(leagueEntity.name).isEqualTo(remoteLeague.strTeam)
        assertThat(leagueEntity.sport).isEqualTo(remoteLeague.strSport)
        assertThat(leagueEntity.imageUrl).isEqualTo(remoteLeague.strTeamBadge)
    }
}