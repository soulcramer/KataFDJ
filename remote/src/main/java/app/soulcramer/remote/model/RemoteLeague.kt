package app.soulcramer.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteLeague(
    val idLeague: String,
    val strLeague: String,
    val strSport: String
)

@JsonClass(generateAdapter = true)
class ResponseLeague(val leagues: List<RemoteLeague>?)