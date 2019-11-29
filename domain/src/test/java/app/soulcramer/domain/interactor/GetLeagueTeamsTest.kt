package app.soulcramer.domain.interactor

import app.soulcramer.domain.repository.LeagueRepository
import app.soulcramer.domain.test.TeamFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLeagueTeamsTest {

    private lateinit var getLeagueTeams: GetLeagueTeams

    private lateinit var mockLeagueRepository: LeagueRepository

    @Before
    fun setUp() {
        mockLeagueRepository = mockk()
        getLeagueTeams = GetLeagueTeams(mockLeagueRepository)
    }

    @Test
    fun `Given empty league name When getting the teams by league name Then return empty list`() {
        runBlocking {
            coEvery { mockLeagueRepository.getTeams(any()) } returns emptyList()

            val result = getLeagueTeams("")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given non existing league name When getting the teams by league name Then return empty list`() {
        runBlocking {
            coEvery { mockLeagueRepository.getTeams(any()) } returns emptyList()

            val result = getLeagueTeams("234")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given existing league name When getting the teams by league name Then return teams`() {
        runBlocking {
            val testTeam = TeamFactory.makeTeamList(3)
            coEvery { mockLeagueRepository.getTeams(any()) } returns testTeam

            val result = getLeagueTeams("123")

            assertThat(result).containsExactlyElementsIn(testTeam)
        }
    }
}