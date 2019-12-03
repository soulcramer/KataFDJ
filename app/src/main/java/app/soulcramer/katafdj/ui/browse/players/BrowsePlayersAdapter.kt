package app.soulcramer.katafdj.ui.browse.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.soulcramer.katafdj.R
import app.soulcramer.presentation.model.PlayerView
import com.bumptech.glide.Glide

class BrowsePlayersAdapter(private var players: List<PlayerView>) :
    RecyclerView.Adapter<BrowsePlayersAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var name: TextView = rootView.findViewById(R.id.team_name_text_view)
        var position: TextView = rootView.findViewById(R.id.player_position_text_view)
        var birthday: TextView = rootView.findViewById(R.id.player_birthday_text_view)
        var signin: TextView = rootView.findViewById(R.id.player_siging_text_view)
        var profileImage: ImageView = rootView.findViewById(R.id.player_image_view)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerView = players[position]
        holder.name.text = playerView.fullname
        holder.position.text = playerView.position
        holder.birthday.text = playerView.birthday
        holder.signin.text = playerView.signin
        Glide.with(holder.itemView)
            .load(playerView.imageUrl)
            .into(holder.profileImage)
    }

    override fun getItemCount() = players.size

    fun setPlayers(newPlayers: List<PlayerView>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}