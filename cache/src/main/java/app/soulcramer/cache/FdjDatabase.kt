package app.soulcramer.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import app.soulcramer.cache.model.CachedLeague

@Database(
    entities = [
        CachedLeague::class
    ],
    version = 1
)
abstract class FdjDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
}