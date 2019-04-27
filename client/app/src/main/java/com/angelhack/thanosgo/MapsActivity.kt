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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.jetbrains.anko.toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.OnSuccessListener
import org.jetbrains.anko.support.v4.startActivity


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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
         activities = intent.getParcelableArrayListExtra<Point>(ACTIVITIES)

        activities?.mapIndexed { index, point ->
            val latLng = LatLng(point.latitude, point.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(point.event)).tag = index
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(activities[0].latitude, activities[0].longitude), 1f))
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
