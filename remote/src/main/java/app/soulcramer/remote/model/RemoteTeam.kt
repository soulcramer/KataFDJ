package app.soulcramer.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteTeam(
    val idTeam: String,
    val strTeam: String,
    val strSport: String,
    val strTeamBadge: String
)

@JsonClass(generateAdapter = true)
class ResponseTeam(val teams: List<RemoteTeam>?)