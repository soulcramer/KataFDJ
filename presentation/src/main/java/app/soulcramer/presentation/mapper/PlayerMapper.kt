package app.soulcramer.presentation.mapper

import app.soulcramer.domain.model.Player
import app.soulcramer.presentation.model.PlayerView

open class PlayerMapper : Mapper<PlayerView, Player> {

    override fun mapToView(type: Player): PlayerView {
        return PlayerView(type.fullname, type.position, type.signin, type.imageUrl)
    }
}

