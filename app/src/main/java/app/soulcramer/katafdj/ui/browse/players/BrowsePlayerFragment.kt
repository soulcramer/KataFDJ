package app.soulcramer.katafdj.ui.browse.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.soulcramer.katafdj.R
import app.soulcramer.presentation.browse.players.BrowsePlayersContract
import app.soulcramer.presentation.model.PlayerView
import kotlinx.android.synthetic.main.fragment_browse_players.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BrowsePlayerFragment : Fragment(), BrowsePlayersContract.View {

    private val args by navArgs<BrowsePlayerFragmentArgs>()

    private val browsePresenter: BrowsePlayersContract.Presenter by inject {
        parametersOf(this, lifecycleScope, args.teamName)
    }

    private val playersAdapter: BrowsePlayersAdapter by lazy {
        BrowsePlayersAdapter(emptyList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_browse_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players_recycler_view.run {
            layoutManager = LinearLayoutManager(context)
            adapter = playersAdapter
            setHasFixedSize(true)
        }

        browsePresenter.start()
    }

    override fun showProgress() {
        progress_bard.show()
    }

    override fun hideProgress() {
        progress_bard.hide()
    }

    override fun showPlayers(players: List<PlayerView>) {
        players_recycler_view.isVisible = true
        playersAdapter.setPlayers(players)
    }

    override fun hidePlayers() {
        players_recycler_view.isVisible = false
    }

    override fun showEmptyState() {
    }

    override fun hideEmptyState() {
    }
}
