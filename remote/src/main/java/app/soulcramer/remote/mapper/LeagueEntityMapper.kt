package app.soulcramer.remote.mapper

import app.soulcramer.domain.model.League
import app.soulcramer.remote.model.RemoteLeague

open class LeagueEntityMapper : EntityMapper<RemoteLeague, League> {

    override fun mapFromRemote(type: RemoteLeague): League {
        return League(
            type.idLeague,
            type.strLeague,
            type.strSport
        )
    }
}