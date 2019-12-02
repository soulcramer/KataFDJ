package app.soulcramer.presentation.browse.teams

import app.soulcramer.presentation.BasePresenter
import app.soulcramer.presentation.BaseView
import app.soulcramer.presentation.model.LeagueView
import app.soulcramer.presentation.model.TeamView

/**
 * Defines a contract of operations between the Browse Presenter and Browse View
 */
interface BrowseTeamsContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showTeams(teams: List<TeamView>)

        fun loadLeagues(leagues: List<LeagueView>)

        fun hideTeams()

        fun showEmptyState()

        fun hideEmptyState()

    }

    interface Presenter : BasePresenter {

        suspend fun retrieveTeams(leagueName: String)

        suspend fun retrieveLeagues()

    }

}