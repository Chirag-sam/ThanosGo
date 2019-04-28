package com.angelhack.thanosgo.fragments


import android.Manifest
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.amplify.generated.graphql.ListEventsQuery
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers
import com.angelhack.thanosgo.MainActivity
import com.angelhack.thanosgo.adapters.EventsAdapter
import com.angelhack.thanosgo.MapsActivity
import com.angelhack.thanosgo.R
import com.angelhack.thanosgo.models.Event
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_events.view.*
import org.jetbrains.anko.support.v4.startActivity
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AfterPermissionGranted


@Parcelize
class Point(val id: Int, val location: String, val event: String) : Parcelable


const val RC_LOCATION = 23

class EventsFragment : Fragment() {
    private var eventType: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_events, container, false)
        val events = listOf(
            Event("", "Garbage", "Cleanup areas around you"),
            Event("", "Plant Trees", "Create your very own personal garden"),
            Event("", "Recycle waste", "Help make the planet sustainable"),
            Event("", "Don't waste food", "Share food to those in need")
        )

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
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this.activity?.applicationContext!!, *perms)) {
                getEvents()

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

    private val eventsCallback = object : GraphQLCall.Callback<ListEventsQuery.Data>() {
        override fun onResponse(response: Response<ListEventsQuery.Data>) {
            val events = response.data()?.listEvents()?.items()

            val eventsMap = events?.map {
                Event(title = it.title(), id = it.id(), description = it.description(), location = it.location())
            }
            startActivity<MapsActivity>(MapsActivity.eventType to eventType, MapsActivity.ACTIVITIES to eventsMap)

        }

        override fun onFailure(e: ApolloException) {
            Log.e("Error", e.toString())
        }
    }

    fun getEvents () {
        (activity as MainActivity).mAWSAppSyncClient.query(ListEventsQuery.builder().build())
            .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
            .enqueue(eventsCallback)
    }
}
