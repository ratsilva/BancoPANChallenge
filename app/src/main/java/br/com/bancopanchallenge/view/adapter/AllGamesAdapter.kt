package br.com.bancopanchallenge.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.view.activity.GameActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_game.view.*

class AllGamesAdapter : PagedListAdapter<Game, AllGamesAdapter.GameViewHolder>(GameDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGamesAdapter.GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {

        val item = getItem(position)

        holder.itemView.listitem_name.text = item?.name

        Glide
            .with(holder.itemView.context)
            .load(item?.logo)
            .fitCenter()
            .placeholder(R.drawable.progress_animation)
            .into(holder.itemView.listitem_logo);

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, GameActivity::class.java)
            i.putExtra("name", item?.name)
            i.putExtra("channels", item?.channels)
            i.putExtra("viewers", item?.viewers)
            i.putExtra("logo", item?.logo)
            holder.itemView.context.startActivity(i)
        }

    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    class GameDiffUtilCallback : DiffUtil.ItemCallback<Game>() {

        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.viewers == newItem.viewers
                    && oldItem.channels == newItem.channels
                    && oldItem.logo == newItem.logo
        }
    }
}
