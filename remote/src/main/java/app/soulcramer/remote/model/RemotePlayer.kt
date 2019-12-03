package app.soulcramer.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemotePlayer(
    val idTeam: String,
    val strPlayer: String,
    val strPosition: String,
    val strSigning: String,
    val dateBorn: String,
    val strCutout: String?,
    val strRender: String?,
    val strThumb: String?
)

@JsonClass(generateAdapter = true)
class ResponsePlayer(val player: List<RemotePlayer>?)