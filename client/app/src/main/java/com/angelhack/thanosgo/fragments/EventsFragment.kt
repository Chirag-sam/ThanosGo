package com.angelhack.thanosgo.fragments


import android.Manifest
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelhack.thanosgo.adapters.EventsAdapter
import com.angelhack.thanosgo.MapsActivity
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Event

import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_events.view.*
import org.jetbrains.anko.support.v4.startActivity
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AfterPermissionGranted



@Parcelize
class Point(val id: Int, val latitude: Double, val longitude: Double, val event: String) : Parcelable


const val RC_LOCATION = 23
class EventsFragment : Fragment() {
    private var eventType: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_events, container, false)
        val events = listOf<Event>(Event("", "Garbage", "sdsdsdsd"),
                                   Event("", "Plant Trees", "dsdsdsdsdsd"))

        rootView.eventsRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        rootView.eventsRecyclerView.adapter = EventsAdapter(events) {
            eventType = it.title
            requestLocationPermission()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    @AfterPermissionGranted(RC_LOCATION)
    private fun requestLocationPermission() {
        val perms = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this.activity?.applicationContext!!, *perms)) {

            val activities = listOf<Point>(Point(1, 12.343, 80.232, "garbage"),
                Point(1, 12.3232, 80.564, "garbage"),
                Point(1, 12.3544, 80.2324, "plant tree"),
                Point(1, 12.652, 80.465, "garbage"),
                Point(1, 12.122, 80.2452, "plant tree"))
            startActivity<MapsActivity>(MapsActivity.eventType to eventType, MapsActivity.ACTIVITIES to activities)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.location_permission_text),
                RC_LOCATION, *perms
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }



}
