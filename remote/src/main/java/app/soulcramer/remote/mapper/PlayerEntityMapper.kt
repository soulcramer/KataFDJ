package app.soulcramer.remote.mapper

import app.soulcramer.domain.model.Player
import app.soulcramer.remote.model.RemotePlayer

open class PlayerEntityMapper : EntityMapper<RemotePlayer, Player> {

    override fun mapFromRemote(type: RemotePlayer): Player {
        return Player(
            type.idTeam,
            type.strPlayer,
            type.strPosition,
            type.strSigning,
            type.strCutout
        )
    }
}