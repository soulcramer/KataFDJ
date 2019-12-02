package app.soulcramer.presentation.mapper

import app.soulcramer.domain.model.League
import app.soulcramer.presentation.model.LeagueView

open class LeagueMapper : Mapper<LeagueView, League> {

    override fun mapToView(type: League): LeagueView {
        return LeagueView(type.name)
    }
}