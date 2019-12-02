package app.soulcramer.domain.interactor

import app.soulcramer.domain.repository.team.TeamRemoteStore
import app.soulcramer.domain.test.PlayerFactory
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
class GetTeamPlayersTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var getTeamPlayers: GetTeamPlayers

    private lateinit var mockteamRemoteStore: TeamRemoteStore

    @Before
    fun setUp() {
        mockteamRemoteStore = mockk()
        getTeamPlayers = GetTeamPlayers(mockteamRemoteStore, Dispatchers.Main)
    }

    @Test
    fun `Given empty team name When getting the players by team name Then return empty list`() {
        coroutineRule.runBlockingTest {
            coEvery { mockteamRemoteStore.getPlayers(any()) } returns emptyList()

            val result = getTeamPlayers("")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given non existing team name When getting the players by team name Then return empty list`() {
        coroutineRule.runBlockingTest {
            coEvery { mockteamRemoteStore.getPlayers(any()) } returns emptyList()

            val result = getTeamPlayers("234")

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given existing team name When getting the players by team name Then return players`() {
        coroutineRule.runBlockingTest {
            val testPlayers = PlayerFactory.makePlayerList(3)
            coEvery { mockteamRemoteStore.getPlayers(any()) } returns testPlayers

            val result = getTeamPlayers("123")

            assertThat(result).containsExactlyElementsIn(testPlayers)
        }
    }
}