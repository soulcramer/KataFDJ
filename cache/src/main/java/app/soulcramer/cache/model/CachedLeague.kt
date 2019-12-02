package app.soulcramer.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
class CachedLeague(
    @PrimaryKey
    val id: String,
    val name: String,
    val sport: String
)