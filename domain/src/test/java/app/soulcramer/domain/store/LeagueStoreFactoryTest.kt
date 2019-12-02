package app.soulcramer.domain.store

import app.soulcramer.domain.repository.league.LeagueCacheStore
import app.soulcramer.domain.repository.league.LeagueRemoteStore
import app.soulcramer.domain.repository.league.LeagueStoreFactory
import app.soulcramer.domain.test.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LeagueStoreFactoryTest {

    private lateinit var leagueStoreFactory: LeagueStoreFactory

    private lateinit var mockLeagueRemoteStore: LeagueRemoteStore
    private lateinit var mockLeagueCacheStore: LeagueCacheStore

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        mockLeagueRemoteStore = mockk()
        mockLeagueCacheStore = mockk()
        leagueStoreFactory = LeagueStoreFactory(mockLeagueCacheStore, mockLeagueRemoteStore)
    }

    @Test
    fun `Given leagues not cached When getting store Then return remote store`() {
        coroutineRule.runBlockingTest {
            mockLeagueCacheStoreIsCached(false)

            val userDataStore = leagueStoreFactory.retrieveStore()

            assertThat(userDataStore).isInstanceOf(LeagueRemoteStore::class.java)
        }
    }

    @Test
    fun `Given leagues cached and cache expired When getting store Then return remote store`() {
        coroutineRule.runBlockingTest {
            mockLeagueCacheStoreIsCached(true)
            mockLeagueCacheStoreIsExpired(true)

            val userDataStore = leagueStoreFactory.retrieveStore()

            assertThat(userDataStore).isInstanceOf(LeagueRemoteStore::class.java)
        }
    }

    @Test
    fun `Given leagues cached and cache not expired When getting store Then return cache store`() {
        coroutineRule.runBlockingTest {
            mockLeagueCacheStoreIsCached(true)
            mockLeagueCacheStoreIsExpired(false)

            val userDataStore = leagueStoreFactory.retrieveStore()

            assertThat(userDataStore).isInstanceOf(LeagueCacheStore::class.java)
        }
    }

    // <editor-fold desc="Mock helper methods">
    private fun mockLeagueCacheStoreIsCached(isCached: Boolean) {
        coEvery { mockLeagueCacheStore.isCached() } returns isCached
    }

    private fun mockLeagueCacheStoreIsExpired(isExpired: Boolean) {
        coEvery { mockLeagueCacheStore.isExpired() } returns isExpired
    }
    // </editor-fold>
}