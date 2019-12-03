package app.soulcramer.remote

import app.soulcramer.remote.mapper.PlayerEntityMapper
import app.soulcramer.remote.model.ResponsePlayer
import app.soulcramer.remote.test.RemotePlayerFactory
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
class TeamRemoteImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var playerEntityMapper: PlayerEntityMapper

    @MockK
    private lateinit var fdjService: FdjService

    private lateinit var teamRemoteImpl: TeamRemoteImpl

    @Before
    fun setUp() {
        fdjService = mockk()
        playerEntityMapper = PlayerEntityMapper()
        teamRemoteImpl = TeamRemoteImpl(fdjService, playerEntityMapper)
    }

    @Test
    fun `Given a successful response When requesting players from remote Then get all players from team`() =
        coroutineRule.runBlockingTest {
            val stubPlayers = RemotePlayerFactory.makePlayerList(5)
            coEvery { fdjService.getTeamPlayers(any()) } returns ResponsePlayer(stubPlayers)

            val players = teamRemoteImpl.getPlayers("abc")

            coVerify { fdjService.getTeamPlayers(any()) }
            assertThat(players).hasSize(stubPlayers.size)
        }
}