package com.caca.eventapp.utils.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caca.eventapp.R
import com.caca.eventapp.data.response.ListEventsItem
import com.caca.eventapp.ui.detail.DetailActivity

class HorizontalListEventAdapter (private val listEvent: List<ListEventsItem>) : RecyclerView.Adapter<HorizontalListEventAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage : ImageView = itemView.findViewById(R.id.item_horizontal_image)
        val itemName: TextView = itemView.findViewById(R.id.item_horizontal_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_horizontal, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = listEvent[position]
        holder.itemName.text = event.name
        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener{
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_ID, event.id)
            holder.itemView.context.startActivity(moveIntent)
        }
    }

    override fun getItemCount(): Int = listEvent.size.coerceAtMost(5)


}