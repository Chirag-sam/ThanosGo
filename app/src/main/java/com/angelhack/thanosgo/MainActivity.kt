package com.angelhack.thanosgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import android.view.MenuItem
import androidx.annotation.NonNull
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!


        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        toolbar?.setTitle("Uno")
        loadFragment(ActivitiesFragment())
    }

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.activities -> {
                    toolbar.title = "Activities"
                    loadFragment(ActivitiesFragment())
                    return true
                }
                R.id.feed -> {
                    toolbar.title = "Feed"
                    loadFragment(ActivitiesFragment())
                    return true
                }
                R.id.profile -> {
                    toolbar.title = "Profile"
                    loadFragment(ActivitiesFragment())
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
}
