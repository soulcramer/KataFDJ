package app.soulcramer.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.soulcramer.cache.FdjDatabase
import app.soulcramer.cache.test.CachedLeagueFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
open class CachedLeagueDaoTest {

    private lateinit var fdjDatabase: FdjDatabase

    @Before
    fun initDb() {
        fdjDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FdjDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        fdjDatabase.close()
    }

    @Test
    fun `Given leagues to save When saving them Then all leagues should be saved`() {
        runBlocking {
            val initialLeaguesCount = fdjDatabase.leagueDao().allLeaguesCount()
            assertThat(initialLeaguesCount).isEqualTo(0)

            val cachedLeagues = CachedLeagueFactory.makeLeagueList(5)
            fdjDatabase.leagueDao().insertLeagues(cachedLeagues)

            val leaguesCount = fdjDatabase.leagueDao().allLeaguesCount()
            assertThat(leaguesCount).isEqualTo(cachedLeagues.size)
        }
    }

    @Test
    fun `Given saved leagues in db When getting all of them Then return all cached leagues`() {
        runBlocking {
            val cachedLeagues = CachedLeagueFactory.makeLeagueList(5)
            fdjDatabase.leagueDao().insertLeagues(cachedLeagues)

            val leaguesCount = fdjDatabase.leagueDao().getAll()
            assertThat(leaguesCount).hasSize(cachedLeagues.size)
        }
    }
}