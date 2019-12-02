package app.soulcramer.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.soulcramer.cache.model.CachedLeague

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leagues: List<CachedLeague>)

    @Query("SELECT * FROM leagues")
    suspend fun getAll(): List<CachedLeague>

    @Query("SELECT count(id) FROM leagues")
    suspend fun allLeaguesCount(): Int
}
