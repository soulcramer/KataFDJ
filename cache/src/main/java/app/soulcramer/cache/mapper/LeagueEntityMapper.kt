package app.soulcramer.cache.mapper

import app.soulcramer.cache.model.CachedLeague
import app.soulcramer.domain.model.League

class LeagueEntityMapper : EntityMapper<CachedLeague, League> {

    override fun mapFromCached(type: CachedLeague): League = League(type.id, type.name, type.sport)

    override fun mapToCached(type: League): CachedLeague {
        return CachedLeague(type.id, type.name, type.sport)
    }
}