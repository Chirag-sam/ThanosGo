package com.angelhack.thanosgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelhack.thanosgo.adapters.PointsAdapter
import com.angelhack.thanosgo.fragments.Point
import kotlinx.android.synthetic.main.activity_point_list.*

class PointListActivity : AppCompatActivity() {
    companion object {
        const val POINTS = "PointsListActivity:points"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_list)

        val points = intent.getParcelableArrayListExtra<Point>(POINTS)

        title = "Points"
        pointsRecyclerView.layoutManager = LinearLayoutManager(this)
        pointsRecyclerView.adapter = PointsAdapter(points) {}

    }
}
