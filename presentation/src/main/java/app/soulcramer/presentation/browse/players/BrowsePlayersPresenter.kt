package app.soulcramer.presentation.browse.players

import app.soulcramer.domain.interactor.GetTeamPlayers
import app.soulcramer.presentation.mapper.PlayerMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BrowsePlayersPresenter(
    val browseView: BrowsePlayersContract.View,
    val getTeamPlayers: GetTeamPlayers,
    val playerMapper: PlayerMapper,
    val coroutineScope: CoroutineScope,
    val teamName: String
) : BrowsePlayersContract.Presenter {

    override fun start() {
        coroutineScope.launch {
            retrievePlayers(teamName)
        }
    }

    override fun stop() {
    }

    override suspend fun retrievePlayers(teamName: String) {
        browseView.showProgress()
        val players = getTeamPlayers(teamName).map(playerMapper::mapToView)
        browseView.hideProgress()
        if (players.isEmpty()) {
            browseView.hidePlayers()
            browseView.showEmptyState()
        } else {
            browseView.hideEmptyState()
            browseView.showPlayers(players)
        }
    }
}