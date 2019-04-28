package com.angelhack.thanosgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelhack.thanosgo.FeedAdapter

import com.angelhack.thanosgo.R
import kotlinx.android.synthetic.main.fragment_feed.*

// TODO: Rename parameter arguments, choose names that match
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

        feedRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        feedRecyclerView.adapter = FeedAdapter(listOf())
        return rootView
    }


}
