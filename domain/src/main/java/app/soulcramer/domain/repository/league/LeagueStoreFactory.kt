package app.soulcramer.domain.repository.league

open class LeagueStoreFactory(
    private val leagueCacheStore: LeagueCacheStore,
    private val leagueRemoteStore: LeagueRemoteStore
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open suspend fun retrieveStore(): LeagueStore {
        if (leagueCacheStore.isCached() && !leagueCacheStore.isExpired()) {
            return leagueCacheStore
        }
        return leagueRemoteStore
    }
}