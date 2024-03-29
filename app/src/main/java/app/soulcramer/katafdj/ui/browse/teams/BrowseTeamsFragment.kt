package app.soulcramer.katafdj.ui.browse.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import app.soulcramer.katafdj.R
import app.soulcramer.presentation.browse.teams.BrowseTeamsContract
import app.soulcramer.presentation.model.LeagueView
import app.soulcramer.presentation.model.TeamView
import kotlinx.android.synthetic.main.fragment_browse_teams.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class BrowseTeamsFragment : Fragment(), BrowseTeamsContract.View {

    private var browsePresenter: BrowseTeamsContract.Presenter? = null

    private var leagueName: String = ""

    private val teamsAdapter: BrowseTeamsAdapter by lazy {
        BrowseTeamsAdapter(emptyList())
    }

    private val leagueAdapter by lazy {
        ArrayAdapter<String>(requireContext(), R.layout.item_league, mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_browse_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsAdapter.setOnItemClickListener(object : BrowseTeamsAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, direction: NavDirections, position: Int) {
                findNavController().navigate(direction)
            }
        })

        leagueName = if (savedInstanceState != null) {
            savedInstanceState.getString(LEAGUE_NAME_KEY, "")
        } else {
            ""
        }

        browsePresenter = get { parametersOf(this, lifecycleScope, leagueName) }
        empty_title_text_view.text = getString(R.string.teams_empty_title, leagueName)

        teams_recycler_view.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = teamsAdapter
        }

        browsePresenter?.start()

        search_league_text_input_layout.editText?.doOnTextChanged { text, _, _, _ ->
            browsePresenter?.retrieveTeams(text.toString())
        }

        search_league_auto_complete_text_view.setAdapter(leagueAdapter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEAGUE_NAME_KEY, leagueName)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        browsePresenter?.stop()
        browsePresenter = null
        super.onDestroyView()
    }

    override fun showProgress() {
        progress_bard.show()
    }

    override fun hideProgress() {
        progress_bard.hide()
    }

    override fun showTeams(teams: List<TeamView>) {
        teams_recycler_view.isVisible = true
        teamsAdapter.setTeams(teams)
    }

    override fun loadLeagues(leagues: List<LeagueView>) {
        leagueAdapter.addAll(leagues.map { it.name })
        leagueAdapter.notifyDataSetChanged()
    }

    override fun hideTeams() {
        teams_recycler_view.isVisible = false
    }

    override fun showEmptyState() {
        empty_title_text_view.isVisible = true
    }

    override fun hideEmptyState() {
        empty_title_text_view.isVisible = false
    }

    companion object {
        private const val LEAGUE_NAME_KEY = "bundle.league.name"
    }
}
