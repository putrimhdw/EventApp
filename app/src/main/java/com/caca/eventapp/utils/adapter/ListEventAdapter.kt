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

class ListEventAdapter (private val listEvent: List<ListEventsItem>) : RecyclerView.Adapter<ListEventAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage : ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = listEvent[position]
        holder.itemName.text = event.name
        holder.itemDescription.text = HtmlCompat.fromHtml(
            event.summary.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener{
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_ID, event.id)
            holder.itemView.context.startActivity(moveIntent)
        }
    }

    override fun getItemCount(): Int = listEvent.size

}