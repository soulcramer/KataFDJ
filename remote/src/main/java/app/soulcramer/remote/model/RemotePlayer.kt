package app.soulcramer.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemotePlayer(
    val idTeam: String,
    val strPlayer: String,
    val strPosition: String,
    val strSigning: String,
    val strCutout: String
)

@JsonClass(generateAdapter = true)
class ResponsePlayer(val players: List<RemotePlayer>?)