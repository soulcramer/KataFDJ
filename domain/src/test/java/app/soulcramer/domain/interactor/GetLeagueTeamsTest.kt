package app.soulcramer.domain.interactor

import app.soulcramer.domain.repository.league.LeagueRemoteStore
import app.soulcramer.domain.test.TeamFactory
import app.soulcramer.domain.test.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLeagueTeamsTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var getLeagueTeams: GetLeagueTeams

    private lateinit var mockLeagueRemoteStore: LeagueRemoteStore

    @Before
    fun setUp() {
        mockLeagueRemoteStore = mockk()
        getLeagueTeams = GetLeagueTeams(mockLeagueRemoteStore, Dispatchers.Main)
    }

    @Test
    fun `Given empty league name When getting the teams by league name Then return empty list`() {
        coroutineRule.runBlockingTest {
            coEvery { mockLeagueRemoteStore.getTeams(any()) } returns emptyList()

            val result = getLeagueTeams("")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given non existing league name When getting the teams by league name Then return empty list`() {
        coroutineRule.runBlockingTest {
            coEvery { mockLeagueRemoteStore.getTeams(any()) } returns emptyList()

            val result = getLeagueTeams("234")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given existing league name When getting the teams by league name Then return teams`() {
        coroutineRule.runBlockingTest {
            val testTeams = TeamFactory.makeTeamList(3)
            coEvery { mockLeagueRemoteStore.getTeams(any()) } returns testTeams

            val result = getLeagueTeams("123")

            assertThat(result).containsExactlyElementsIn(testTeams)
        }
    }

    @Test
    fun `Given existing league name with non soccer teams When getting the teams by league name Then return only soccer teams`() {
        coroutineRule.runBlockingTest {
            val testTeams = TeamFactory.makeTeamList(10, hasOtherSportThanSoccer = true)
            coEvery { mockLeagueRemoteStore.getTeams(any()) } returns testTeams

            val result = getLeagueTeams("123")

            val cleanTeams = testTeams.filter { it.sport == "Soccer" }
            assertThat(result).containsExactlyElementsIn(cleanTeams)
        }
    }

    @Test
    fun `Given existing league name When getting the teams by league name Then return teams ordered by team name`() {
        coroutineRule.runBlockingTest {
            val testTeams = TeamFactory.makeTeamList(10).shuffled()
            coEvery { mockLeagueRemoteStore.getTeams(any()) } returns testTeams

            val result = getLeagueTeams("123")

            val orderedTeams = testTeams.sortedBy { it.name }
            assertThat(result).containsExactlyElementsIn(orderedTeams)
        }
    }
}