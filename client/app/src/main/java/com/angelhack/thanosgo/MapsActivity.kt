package com.angelhack.thanosgo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.angelhack.thanosgo.fragments.Point

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import org.jetbrains.anko.toast


import android.graphics.BitmapFactory
import android.graphics.Bitmap




class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private var markers = mutableListOf<Marker>()

    companion object {
        val eventType = "EVENT_TYPE"
        val ACTIVITIES = "EVENTS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val eventName = intent.getStringExtra(eventType)




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    private var activities = listOf<Point>()

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
        mMap.isMyLocationEnabled = true
         activities = intent.getParcelableArrayListExtra<Point>(ACTIVITIES)

        activities?.mapIndexed { index, point ->
            val latLng = LatLng(point.latitude, point.longitude)
            val markerOptions = MarkerOptions().position(latLng).title(point.event)
            if (point.event == "garbage")
                else if (point.event == "tree")
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mMap.addMarker(markerOptions).tag = index
        }


        mMap.addMarker(MarkerOptions().position(LatLng(12.972849, 80.238182))
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(12.972849, 80.238182), 1f))
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f), 3000, null)

        mMap.setOnMarkerClickListener(this)

    } override fun onMarkerClick(marker: Marker?): Boolean {

        val index = marker?.tag as Int
        val activity = activities[index]



        return false

    }




}
