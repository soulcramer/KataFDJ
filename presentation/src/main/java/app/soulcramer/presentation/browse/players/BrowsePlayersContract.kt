package app.soulcramer.presentation.browse.players

import app.soulcramer.presentation.BasePresenter
import app.soulcramer.presentation.BaseView
import app.soulcramer.presentation.model.PlayerView

interface BrowsePlayersContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showPlayers(players: List<PlayerView>)

        fun hidePlayers()

        fun showEmptyState()

        fun hideEmptyState()

    }

    interface Presenter : BasePresenter {

        suspend fun retrievePlayers(teamName: String)

    }
}