package com.angelhack.thanosgo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelhack.thanosgo.models.Feed
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(val feedItems: List<Feed>): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
           val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int  = feedItems.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bindFeedItem(feedItems[position])


    }


    inner class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindFeedItem(feed: Feed) {
            with(feed) {
                itemView.eventName.text = name
                itemView.timeTakenTv.text = "$timeTaken mins"
                itemView.timeTv.text = "Today at $time"
                itemView.eventDescTv.text = desc
                itemView.pointsTv.text = points.toString()

            }
        }
    }
}