package app.soulcramer.presentation.browse.players

import app.soulcramer.domain.interactor.GetTeamPlayers
import app.soulcramer.presentation.mapper.PlayerMapper
import app.soulcramer.presentation.test.PlayerFactory
import app.soulcramer.presentation.test.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BrowsePlayersPresenterTest {

    private lateinit var mockBrowsePlayersView: BrowsePlayersContract.View
    private lateinit var playerMapper: PlayerMapper
    private lateinit var mockGetLeaguePlayers: GetTeamPlayers

    private lateinit var browsePlayersPresenter: BrowsePlayersPresenter

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        mockBrowsePlayersView = mockk()
        playerMapper = PlayerMapper()
        mockGetLeaguePlayers = mockk()

        every { mockBrowsePlayersView.setPresenter(any()) } just Runs
        every { mockBrowsePlayersView.showProgress() } just Runs
        every { mockBrowsePlayersView.hideProgress() } just Runs
        every { mockBrowsePlayersView.hidePlayers() } just Runs
        every { mockBrowsePlayersView.hideEmptyState() } just Runs
        every { mockBrowsePlayersView.showEmptyState() } just Runs
        every { mockBrowsePlayersView.showPlayers(any()) } just Runs

        browsePlayersPresenter = BrowsePlayersPresenter(
            mockBrowsePlayersView,
            mockGetLeaguePlayers,
            playerMapper,
            coroutineRule,
            "team name"
        )
    }

    @Test
    fun `Given empty players When retrieving them Then show emptystate and hide players`() =
        coroutineRule.runBlockingTest {
            coEvery { mockGetLeaguePlayers(any()) } returns emptyList()

            browsePlayersPresenter.retrievePlayers("abc")

            verify { mockBrowsePlayersView.showEmptyState() }
            verify { mockBrowsePlayersView.hidePlayers() }
        }

    @Test
    fun `Given non empty players When retrieving them Then hide emptystate and show players`() =
        coroutineRule.runBlockingTest {
            val testPlayers = PlayerFactory.makePlayerList(3)
            coEvery { mockGetLeaguePlayers(any()) } returns testPlayers

            browsePlayersPresenter.retrievePlayers("abc")

            verify { mockBrowsePlayersView.showPlayers(any()) }
            verify { mockBrowsePlayersView.hideEmptyState() }
        }

    @Test
    fun `Given starting a league search When retrieving it's players Then show and hide loading state`() =
        coroutineRule.runBlockingTest {
            coEvery { mockGetLeaguePlayers(any()) } returns emptyList()

            browsePlayersPresenter.retrievePlayers("abc")

            verify(ordering = Ordering.ORDERED) { mockBrowsePlayersView.showProgress() }
            coVerify(ordering = Ordering.ORDERED) { mockGetLeaguePlayers(any()) }
            verify(ordering = Ordering.ORDERED) { mockBrowsePlayersView.hideProgress() }
        }
}