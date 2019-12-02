package app.soulcramer.remote.mapper

import app.soulcramer.domain.model.Team
import app.soulcramer.remote.model.RemoteTeam

open class TeamEntityMapper : EntityMapper<RemoteTeam, Team> {

    override fun mapFromRemote(type: RemoteTeam): Team {
        return Team(
            type.idTeam,
            type.strTeam,
            type.strSport,
            type.strTeamBadge
        )
    }
}