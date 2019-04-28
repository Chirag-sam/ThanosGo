package com.angelhack.thanosgo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.fragments.Point
import com.angelhack.thanosgo.models.Event
import kotlinx.android.synthetic.main.point_item.view.*

class PointsAdapter(val points: List<Point>, val itemClick: (Event) -> Unit): RecyclerView.Adapter<PointsAdapter.PointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.point_item, parent, false)
        return PointViewHolder(itemView)

    }

    override fun getItemCount() = points.size

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bindEventItem(points[position])
    }


    inner class PointViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindEventItem(point: Point) {

            with(point) {

                itemView.activityNameTv.text = event
            }

        }
    }

}