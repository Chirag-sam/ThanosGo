package com.angelhack.thanosgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelhack.thanosgo.FeedAdapter

import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Feed
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_feed, container, false)
        val feedItems = listOf(Feed("Regina Philange", "19", "12:23 PM","Would love for more people to join me in the quest to change the world. ", 600),
            Feed("Ken Adams", "29", "9:56 PM","I loved doing this task. Was refreshing. Have always wanted to get my hands dirty and help to make this city a better place.", 250),
            Feed("Thor", "54", "6:15 AM","Wow. Absolutely loving this. I get to get off of my ass AND enjoy myself? What sort of sorcery is this??", 500),
            Feed("Iron Man", "3", "6:09 AM","The people who created the app are geniuses. Love them 3000. Hail Hydra!", 6969)
        )

        rootView.feedRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        rootView.feedRecyclerView.adapter = FeedAdapter(feedItems)
        return rootView
    }


}
