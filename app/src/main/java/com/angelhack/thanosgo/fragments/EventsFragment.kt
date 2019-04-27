package com.angelhack.thanosgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelhack.thanosgo.EventsAdapter
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Event
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_activities, container, false)
        val events = listOf<Event>(Event("", "Garbage", "sdsdsdsd"),
                                   Event("", "Plant Trees", "dsdsdsdsdsd"))
        eventsRecyclerView.adapter = EventsAdapter(listOf()) {

            rootView.toast("Clicked! ${it.title}")

        }

        // Inflate the layout for this fragment
        return rootView
    }

    fun itemClick(event: Event) {

    }


}
