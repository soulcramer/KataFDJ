package app.soulcramer.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.soulcramer.cache.mapper.LeagueEntityMapper
import app.soulcramer.cache.model.CachedLeague
import app.soulcramer.cache.test.LeagueFactory
import app.soulcramer.domain.model.League
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
@ExperimentalCoroutinesApi
class LeagueCacheStoreImplTest {

    private var notifyMoeDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        FdjDatabase::class.java
    ).allowMainThreadQueries().build()

    private var leagueEntityMapper = LeagueEntityMapper()
    private var preferencesHelper = PreferencesHelper(ApplicationProvider.getApplicationContext())

    private val leagueCacheStore =
        LeagueCacheStoreImpl(notifyMoeDatabase, leagueEntityMapper, preferencesHelper)

    @Test
    fun `Given a list of leagues When saving them Then leagues should be saved`() {
        runBlocking {
            val leagueCount = 5
            val leagues = LeagueFactory.makeLeagueList(leagueCount)

            leagueCacheStore.saveLeagues(leagues)
            checkNumRowsInUsersTable(leagueCount)
        }
    }

    @Test
    fun `Given saved leagues in db When getting all them Then return all cached leagues`() {
        runBlocking {
            val leagueEntities = LeagueFactory.makeLeagueList(2)
            val cachedLeagues = mutableListOf<CachedLeague>()

            leagueEntities.forEach { leagueEntity: League ->
                cachedLeagues.add(leagueEntityMapper.mapToCached(leagueEntity))
            }
            notifyMoeDatabase.leagueDao().insertLeagues(cachedLeagues)

            val leagues = leagueCacheStore.getAllLeagues()
            assertThat(leagues).containsExactlyElementsIn(leagueEntities)
        }
    }

    private suspend fun checkNumRowsInUsersTable(expectedRows: Int) {
        val numberOfRows = notifyMoeDatabase.leagueDao().allLeaguesCount()
        assertThat(expectedRows).isEqualTo(numberOfRows)
    }
}