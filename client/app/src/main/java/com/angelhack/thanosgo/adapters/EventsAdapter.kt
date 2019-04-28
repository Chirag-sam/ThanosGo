package com.angelhack.thanosgo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Event
import kotlinx.android.synthetic.main.event_item.view.*

class EventsAdapter(val events: List<Event>, val itemClick: (Event) -> Unit): RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(itemView)

    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEventItem(events[position])
    }


    inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindEventItem(event: Event) {

            with(event) {

                itemView.eventName.text = title
                itemView.eventDesc.text = description
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }

        }
    }

}