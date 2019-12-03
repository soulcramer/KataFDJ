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
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class BrowsePlayersFragment : Fragment(), BrowsePlayersContract.View {

    private val args by navArgs<BrowsePlayersFragmentArgs>()

    private var browsePresenter: BrowsePlayersContract.Presenter? = null
    private var teamName: String = ""

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

        teamName = if (savedInstanceState != null) {
            savedInstanceState.getString(TEAM_NAME_KEY, args.teamName)
        } else {
            args.teamName
        }

        browsePresenter = get { parametersOf(this, lifecycleScope, teamName) }

        players_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playersAdapter
            setHasFixedSize(true)
        }

        empty_title_text_view.text = getString(R.string.players_empty_title, teamName)

        browsePresenter?.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(TEAM_NAME_KEY, teamName)
        super.onSaveInstanceState(outState)
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
        empty_title_text_view.isVisible = true
    }

    override fun hideEmptyState() {
        empty_title_text_view.isVisible = false
    }

    companion object {
        private const val TEAM_NAME_KEY = "bundle.team.name"
    }
}
