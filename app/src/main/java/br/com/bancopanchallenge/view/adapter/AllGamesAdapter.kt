package br.com.bancopanchallenge.view.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.app.inflate
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.view.activity.GameActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_game.view.*

class AllGamesAdapter(private val listGames: MutableList<Game>) : RecyclerView.Adapter<AllGamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_game))
    }

    override fun onBindViewHolder(holder: AllGamesAdapter.ViewHolder, position: Int) {

        holder.itemName.text = listGames[position].name

        /*
        Picasso.with(holder.itemView.context)
            .load(listGames[position].logo)
            .noFade()
            .into(holder.itemImage)
            */

        Glide
            .with(holder.itemView.context)
            .load(listGames[position].logo)
            .fitCenter()
            .placeholder(R.drawable.progress_animation)
            .into(holder.itemImage);

        holder.itemView.setOnClickListener(View.OnClickListener {

            val i = Intent(holder.itemView.context, GameActivity::class.java)
            i.putExtra("name", listGames[position].name)
            holder.itemView.context.startActivity(i)


        })

    }

    override fun getItemCount() = listGames.size

    fun updateGames(games: List<Game>) {
        this.listGames.addAll(games)
        notifyDataSetChanged()
    }

    fun clearGames(){
        this.listGames.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage = itemView.listitem_logo
        var itemName = itemView.listitem_name
    }
}