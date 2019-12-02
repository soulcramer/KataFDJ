package app.soulcramer.presentation.browse.teams

import app.soulcramer.domain.interactor.GetAllLeagues
import app.soulcramer.domain.interactor.GetLeagueTeams
import app.soulcramer.presentation.mapper.LeagueMapper
import app.soulcramer.presentation.mapper.TeamMapper
import app.soulcramer.presentation.test.TeamFactory
import app.soulcramer.presentation.test.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BrowseTeamsPresenterTest {

    private lateinit var mockBrowseTeamsView: BrowseTeamsContract.View
    private lateinit var teamMapper: TeamMapper
    private lateinit var leagueMapper: LeagueMapper
    private lateinit var mockGetAllLeagues: GetAllLeagues
    private lateinit var mockGetLeagueTeams: GetLeagueTeams

    private lateinit var browseTeamsPresenter: BrowseTeamsPresenter

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        mockBrowseTeamsView = mockk()
        teamMapper = TeamMapper()
        leagueMapper = LeagueMapper()
        mockGetAllLeagues = mockk()
        mockGetLeagueTeams = mockk()

        every { mockBrowseTeamsView.setPresenter(any()) } just Runs
        every { mockBrowseTeamsView.showProgress() } just Runs
        every { mockBrowseTeamsView.hideProgress() } just Runs
        every { mockBrowseTeamsView.hideTeams() } just Runs
        every { mockBrowseTeamsView.hideEmptyState() } just Runs
        every { mockBrowseTeamsView.showEmptyState() } just Runs
        every { mockBrowseTeamsView.showTeams(any()) } just Runs

        browseTeamsPresenter = BrowseTeamsPresenter(
            mockBrowseTeamsView,
            mockGetAllLeagues,
            mockGetLeagueTeams,
            leagueMapper,
            teamMapper,
            coroutineRule,
            "league name"
        )
    }

    @Test
    fun `Given empty teams When retrieving them Then show emptystate and hide teams`() =
        coroutineRule.runBlockingTest {
            coEvery { mockGetLeagueTeams(any()) } returns emptyList()

            browseTeamsPresenter.retrieveTeams("abc")

            verify { mockBrowseTeamsView.showEmptyState() }
            verify { mockBrowseTeamsView.hideTeams() }
        }

    @Test
    fun `Given non empty teams When retrieving them Then hide emptystate and show teams`() =
        coroutineRule.runBlockingTest {
            val testTeams = TeamFactory.makeTeamList(3)
            coEvery { mockGetLeagueTeams(any()) } returns testTeams

            browseTeamsPresenter.retrieveTeams("abc")

            verify { mockBrowseTeamsView.showTeams(any()) }
            verify { mockBrowseTeamsView.hideEmptyState() }
        }

    @Test
    fun `Given starting a league search When retrieving it's teams Then show and hide loading state`() =
        coroutineRule.runBlockingTest {
            coEvery { mockGetLeagueTeams(any()) } returns emptyList()

            browseTeamsPresenter.retrieveTeams("abc")

            verify(ordering = Ordering.ORDERED) { mockBrowseTeamsView.showProgress() }
            coVerify(ordering = Ordering.ORDERED) { mockGetLeagueTeams(any()) }
            verify(ordering = Ordering.ORDERED) { mockBrowseTeamsView.hideProgress() }
        }
}