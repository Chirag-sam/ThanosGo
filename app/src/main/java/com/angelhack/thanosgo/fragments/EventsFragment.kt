package com.angelhack.thanosgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelhack.thanosgo.EventsAdapter
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Event
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_events, container, false)
        val events = listOf<Event>(Event("", "Garbage", "sdsdsdsd"),
                                   Event("", "Plant Trees", "dsdsdsdsdsd"))

        rootView.eventsRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        rootView.eventsRecyclerView.adapter = EventsAdapter(events) {

            Toast
                .makeText(this.activity, "Clicked! ${it.title}", Toast.LENGTH_SHORT).show()        }

        // Inflate the layout for this fragment
        return rootView
    }

    fun itemClick(event: Event) {

    }


}
