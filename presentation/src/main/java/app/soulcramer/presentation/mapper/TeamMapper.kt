package app.soulcramer.presentation.mapper

import app.soulcramer.domain.model.Team
import app.soulcramer.presentation.model.TeamView

open class TeamMapper : Mapper<TeamView, Team> {

    override fun mapToView(type: Team): TeamView {
        return TeamView(type.name, type.imageUrl)
    }
}
