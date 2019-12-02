package app.soulcramer.domain.repository.league

import app.soulcramer.domain.model.League

interface LeagueCacheStore : LeagueStore {

    suspend fun saveLeagues(leagues: List<League>)

    suspend fun isCached(): Boolean

    fun setLastCacheTime(lastCache: Long)

    fun isExpired(): Boolean
}