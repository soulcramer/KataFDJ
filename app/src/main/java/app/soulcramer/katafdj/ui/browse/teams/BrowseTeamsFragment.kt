package app.soulcramer.katafdj.ui.browse.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import app.soulcramer.katafdj.R
import app.soulcramer.presentation.browse.teams.BrowseTeamsContract
import app.soulcramer.presentation.model.LeagueView
import app.soulcramer.presentation.model.TeamView
import kotlinx.android.synthetic.main.fragment_browse_teams.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit


class BrowseTeamsFragment : Fragment(), BrowseTeamsContract.View {

    private val browsePresenter: BrowseTeamsContract.Presenter by inject {
        parametersOf(this, lifecycleScope, "")
    }

    private val adapter: BrowseTeamsAdapter by lazy {
        BrowseTeamsAdapter(emptyList())
    }

    private val leagueAdapter by lazy {
        ArrayAdapter<String>(
            context,
            R.layout.item_league,
            mutableListOf()
        )
    }

    private var throttleTeamSearchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_browse_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teams_recycler_view.layoutManager = GridLayoutManager(context, 2)
        teams_recycler_view.adapter = adapter

        browsePresenter.start()

        search_league_text_input_layout.editText?.doOnTextChanged { text, _, _, _ ->
            throttleTeamSearchJob?.cancel()
            throttleTeamSearchJob = lifecycleScope.launchWhenResumed {
                delay(TimeUnit.MILLISECONDS.toMillis(500))
                browsePresenter.retrieveTeams(text.toString())
            }
        }

        search_league_auto_complete_text_view.setAdapter(leagueAdapter)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showTeams(teams: List<TeamView>) {
        adapter.setTeams(teams)
    }

    override fun loadLeagues(leagues: List<LeagueView>) {
        leagueAdapter.addAll(leagues.map { it.name })
        leagueAdapter.notifyDataSetChanged()
    }

    override fun hideTeams() {
    }

    override fun showEmptyState() {
    }

    override fun hideEmptyState() {
    }
}
