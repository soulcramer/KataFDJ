package app.soulcramer.katafdj.ui.browse.teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import app.soulcramer.katafdj.R
import app.soulcramer.presentation.model.TeamView
import com.bumptech.glide.Glide


class BrowseTeamsAdapter(private var teams: List<TeamView>) :
    RecyclerView.Adapter<BrowseTeamsAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teamView = teams[position]
        holder.name.text = teamView.name
        Glide.with(holder.itemView)
            .load(teamView.imageUrl)
            .into(holder.badge)

        holder.itemView.setOnClickListener {
            // Triggers click upwards to the adapter on click
            listener?.let {
                if (position != RecyclerView.NO_POSITION) {
                    val teamName = teams[position].name
                    val direction = BrowseTeamsFragmentDirections.navigateToTeam(teamName)
                    it.onItemClick(holder.itemView, direction, position)
                }
            }
        }
    }

    override fun getItemCount() = teams.size

    fun setTeams(newTeams: List<TeamView>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, direction: NavDirections, position: Int)
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var name: TextView = rootView.findViewById(R.id.team_name_text_view)
        var badge: ImageView = rootView.findViewById(R.id.team_badge_image_view)
    }
}