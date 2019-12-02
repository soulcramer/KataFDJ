package app.soulcramer.domain.interactor

import app.soulcramer.domain.repository.league.LeagueRemoteStore
import app.soulcramer.domain.repository.league.LeagueStoreFactory
import app.soulcramer.domain.test.LeagueFactory
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
class GetAllLeaguesTest {

    private lateinit var getAllLeagues: GetAllLeagues

    private lateinit var mockLeagueStoreFactory: LeagueStoreFactory
    private lateinit var mockLeagueStore: LeagueRemoteStore

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        mockLeagueStoreFactory = mockk()
        mockLeagueStore = mockk()
        coEvery { mockLeagueStoreFactory.retrieveStore() } returns mockLeagueStore

        getAllLeagues = GetAllLeagues(mockLeagueStoreFactory, Dispatchers.Main)
    }

    @Test
    fun `Given nonSuccessful league fetch When getting the leagues Then return only the soccer leagues`() {
        coroutineRule.runBlockingTest {
            coEvery { mockLeagueStore.getAllLeagues() } returns emptyList()

            val result = getAllLeagues()

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `Given successful league fetch When getting the leagues Then return leagues`() {
        coroutineRule.runBlockingTest {
            val testLeagues = LeagueFactory.makeLeagueList(3)
            coEvery { mockLeagueStore.getAllLeagues() } returns testLeagues

            val result = getAllLeagues()

            assertThat(result).containsExactlyElementsIn(testLeagues)
        }
    }

    @Test
    fun `Given successful league fetch When getting the leagues Then return only the soccer leagues`() {
        coroutineRule.runBlockingTest {
            val testLeagues = LeagueFactory.makeLeagueList(3, hasOtherSportThanSoccer = true)
            coEvery { mockLeagueStore.getAllLeagues() } returns testLeagues

            val result = getAllLeagues()

            val cleanLeagues = testLeagues.filter { it.sport == "Soccer" }
            assertThat(result).containsExactlyElementsIn(cleanLeagues)
        }
    }

    @Test
    fun `Given successful league fetch When getting the leagues Then return leagues sorted by name`() {
        coroutineRule.runBlockingTest {
            val testLeagues = LeagueFactory.makeLeagueList(10).shuffled()
            coEvery { mockLeagueStore.getAllLeagues() } returns testLeagues

            val result = getAllLeagues()

            val cleanLeagues = testLeagues.sortedBy { it.name }
            assertThat(result).containsExactlyElementsIn(cleanLeagues)
        }
    }
}