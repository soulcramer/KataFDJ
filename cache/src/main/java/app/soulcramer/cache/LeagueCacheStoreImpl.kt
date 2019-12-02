package app.soulcramer.cache

import app.soulcramer.cache.mapper.LeagueEntityMapper
import app.soulcramer.domain.model.League
import app.soulcramer.domain.repository.league.LeagueCacheStore
import java.util.concurrent.TimeUnit

class LeagueCacheStoreImpl(
    val database: FdjDatabase,
    private val leagueEntityMapper: LeagueEntityMapper,
    private val preferencesHelper: PreferencesHelper
) : LeagueCacheStore {

    private val EXPIRATION_TIME = TimeUnit.HOURS.toMillis(3)

    private val leagueDao = database.leagueDao()

    /**
     * Save the given list of [League] instances to the database.
     */
    override suspend fun saveLeagues(leagues: List<League>) {
        val cachedLeagues = leagues.map(leagueEntityMapper::mapToCached)
        return leagueDao.insertLeagues(cachedLeagues)
    }

    /**
     * Checked whether there are instances of [CachedLeague] stored in the cache
     */
    override suspend fun isCached(): Boolean {
        return leagueDao.allLeaguesCount() > 0
    }

    /**
     * Set a point in time at when the cache was last updated
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override suspend fun getAllLeagues(): List<League> {
        val cachedUsers = leagueDao.getAll()
        return cachedUsers.map(leagueEntityMapper::mapFromCached)
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}