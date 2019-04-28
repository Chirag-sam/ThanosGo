package com.angelhack.thanosgo


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.Marker



import com.angelhack.thanosgo.models.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.activity_maps.*



class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private var activities = listOf<Event>()

    companion object {
        val eventType = "EVENT_TYPE"
        val ACTIVITIES = "EVENTS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val eventName = intent.getStringExtra(eventType)
        activities = intent.getParcelableArrayListExtra(ACTIVITIES)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
         activities = intent.getParcelableArrayListExtra<Event>(ACTIVITIES)

        // Add a marker in Sydney and move the camera

        activities.mapIndexed { index, point ->
            val (lat,lng) = point.location.split(",")
            val latLng = LatLng(lat.toDouble(), lng.toDouble())
            val markerOptions = MarkerOptions().position(latLng).title(point.title)
            when {
                point.type == "Planting" -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                point.type == "Trash" -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                point.type == "Recycling" -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                else -> {

                }
            }
            mMap.addMarker(markerOptions).tag = index
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertStringToLocation(activities[0].location), 1f))
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f), 3000, null)

        mMap.setOnMarkerClickListener(this)

    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        val index = marker?.tag as Int
        val activity = activities[index]

        startActivity<PointInfoActivity>(PointInfoActivity.ACTIVITY to activity)
        return false

    }
    fun convertStringToLocation(location:String):LatLng{
        val (lat,lng) = location.split(",")
        return LatLng(lat.toDouble(),lng.toDouble())
    }



}
