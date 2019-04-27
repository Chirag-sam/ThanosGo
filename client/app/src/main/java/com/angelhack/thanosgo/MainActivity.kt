package com.angelhack.thanosgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import android.view.MenuItem
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.angelhack.thanosgo.fragments.EventsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import com.apollographql.apollo.exception.ApolloException
import javax.annotation.Nonnull
import com.apollographql.apollo.GraphQLCall
import android.R.attr.name
import android.R.attr.description
import android.content.Intent
import android.util.Log
import com.apollographql.apollo.api.Response
import com.amazonaws.amplify.generated.graphql.CreateEventMutation
import com.amazonaws.amplify.generated.graphql.ListEventsQuery
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers
import type.CreateEventInput
import android.widget.Toast
import android.view.Menu
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.client.results.SignInResult
import android.R.attr.password
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.UserStateListener
import com.angelhack.thanosgo.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mAWSAppSyncClient: AWSAppSyncClient
    private lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAWSAppSyncClient = AWSAppSyncClient.builder()
            .context(getApplicationContext())
            .awsConfiguration(AWSConfiguration(getApplicationContext()))
            .build()

        AWSMobileClient.getInstance().addUserStateListener { userStateDetails ->
            when (userStateDetails.userState) {
                UserState.SIGNED_OUT -> {
                    val intent = Intent(this@MainActivity, AuthActivity::class.java)
                    // start your next activity
                    finish()
                    startActivity(intent)
                    Log.i("userState", "user signed out")
                }

                else -> Log.i("userState", "unsupported")
            }//Alternatively call .showSignIn()
        }

        toolbar = supportActionBar!!
        runMutation()

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        toolbar.setTitle("Activities")
        loadFragment(EventsFragment())
    }

    private
    val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.activities -> {
                    toolbar.title = "Activities"
                    loadFragment(EventsFragment())
                    return true
                }
                R.id.feed -> {
                    toolbar.title = "Feed"
                    loadFragment(EventsFragment())
                    return true
                }
                R.id.profile -> {
                    toolbar.title = "Profile"
                    loadFragment(ProfileFragment())
                    return true
                }
            }
            return false
        }
    }


    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun runMutation() {
        val createEventInput: CreateEventInput =
            CreateEventInput.builder().title("So much Trash Nea My Place").type("Clean Up")
                .location("65.17229,88.35528").build()

        mAWSAppSyncClient.mutate(CreateEventMutation.builder().input(createEventInput).build())
            .enqueue(mutationCallback)
    }

    private val mutationCallback = object : GraphQLCall.Callback<CreateEventMutation.Data>() {
        override fun onResponse(response: Response<CreateEventMutation.Data>) {
            Log.i("Results", "Added Event" + response.toString())
        }

        override fun onFailure(e: ApolloException) {
            Log.e("Error", e.toString())
        }
    }


    private val eventsCallback = object : GraphQLCall.Callback<ListEventsQuery.Data>() {
        override fun onResponse(response: Response<ListEventsQuery.Data>) {
            Log.i("Results", "Fetched Event" + response.toString())
        }

        override fun onFailure(e: ApolloException) {
            Log.e("Error", e.toString())
        }
    }

    fun getEvents () {
        mAWSAppSyncClient.query(ListEventsQuery.builder().build())
            .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
            .enqueue(eventsCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.log_out -> {
                Toast.makeText(applicationContext, "Log Out", Toast.LENGTH_LONG).show()
                AWSMobileClient.getInstance().signOut()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
