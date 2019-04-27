package com.angelhack.thanosgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.angelhack.thanosgo.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(18.5f, "Garbage disposal"))
        entries.add(PieEntry(26.7f, "Planting trees"))
        val set = PieDataSet(entries, " ")
        set.setDrawValues(false)
        set.setColors(intArrayOf(R.color.green, R.color.yellow, R.color.red, R.color.blue), this.activity)
        val data = PieData(set)
        rootView.chart.setDrawSliceText(false);
        rootView.chart.getDescription().setEnabled(false);
        rootView.chart.setData(data)
        rootView.chart.invalidate() // refresh

        // Inflate the layout for this fragment
        return rootView
    }


}
