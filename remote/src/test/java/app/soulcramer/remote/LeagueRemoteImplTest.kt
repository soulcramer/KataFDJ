package app.soulcramer.remote

import app.soulcramer.remote.mapper.LeagueEntityMapper
import app.soulcramer.remote.mapper.TeamEntityMapper
import app.soulcramer.remote.model.RemoteLeague
import app.soulcramer.remote.model.RemoteTeam
import app.soulcramer.remote.model.ResponseLeague
import app.soulcramer.remote.model.ResponseTeam
import app.soulcramer.remote.test.RemoteLeagueFactory
import app.soulcramer.remote.test.RemoteTeamFactory
import app.soulcramer.remote.test.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LeagueRemoteImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var leagueEntityMapper: LeagueEntityMapper
    private lateinit var teamEntityMapper: TeamEntityMapper

    @MockK
    private lateinit var fdjService: FdjService

    private lateinit var leagueRemoteImpl: LeagueRemoteImpl

    @Before
    fun setUp() {
        fdjService = mockk()
        leagueEntityMapper = LeagueEntityMapper()
        teamEntityMapper = TeamEntityMapper()
        leagueRemoteImpl = LeagueRemoteImpl(fdjService, leagueEntityMapper, teamEntityMapper)
    }

    // <editor-fold desc="Get User">
    @Test
    fun `Given a successful response When requesting leagues from remote Then get all leagues`() =
        coroutineRule.runBlockingTest {
            val stubLeagues = RemoteLeagueFactory.makeLeagueList(5)
            stubFdjServiceGetLeagues(stubLeagues)

            val leagues = leagueRemoteImpl.getAllLeagues()

            coVerify { fdjService.getLeagues() }
            assertThat(leagues).hasSize(stubLeagues.size)
        }

    @Test
    fun `Given a successful response When requesting league teams from remote Then get all teams`() =
        coroutineRule.runBlockingTest {
            val stubTeams = RemoteTeamFactory.makeTeamList(5)
            stubFdjServiceLeagueTeams(stubTeams)

            val teams = leagueRemoteImpl.getTeams("abc")

            coVerify { fdjService.getLeagueTeams(any()) }
            assertThat(teams).hasSize(stubTeams.size)
        }
    // </editor-fold>

    private fun stubFdjServiceGetLeagues(leagues: List<RemoteLeague>) {
        coEvery { fdjService.getLeagues() } returns ResponseLeague(leagues)
    }

    private fun stubFdjServiceLeagueTeams(teams: List<RemoteTeam>) {
        coEvery { fdjService.getLeagueTeams(any()) } returns ResponseTeam(teams)
    }
}