package com.angelhack.thanosgo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity
import com.amazonaws.mobile.client.*

import kotlinx.android.synthetic.main.activity_auth.*
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.UserStateListener
import com.amazonaws.mobile.client.AWSMobileClient


class AuthActivity : AppCompatActivity() {
    private val TAG = "AuthActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

            override fun onResult(userStateDetails: UserStateDetails) {
                when (userStateDetails.userState) {
                    UserState.SIGNED_IN -> {
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        // start your next activity
                        startActivity(intent)
                    }
                    UserState.SIGNED_OUT -> {
                        AWSMobileClient.getInstance().showSignIn(
                            this@AuthActivity,
                            SignInUIOptions.builder()
                                .nextActivity(MainActivity::class.java)
                                .build(),
                            object : Callback<UserStateDetails> {
                                override fun onResult(result: UserStateDetails) {
                                    Log.d(TAG, "onResult: " + result.userState)
                                    when (result.userState) {
                                        UserState.SIGNED_IN -> Log.i("INIT", "logged in!")
                                        UserState.SIGNED_OUT -> Log.i(TAG, "onResult: User did not choose to sign-in")
                                        else -> AWSMobileClient.getInstance().signOut()
                                    }
                                }

                                override fun onError(e: Exception) {
                                    Log.e(TAG, "onError: ", e)
                                }
                            }
                        )
                    }
                    else -> AWSMobileClient.getInstance().signOut()
                }
                Log.i("INIT", "onResult: " + userStateDetails.userState)
            }

            override fun onError(e: Exception) {
                Log.e("INIT", "Initialization error.", e)
            }
        }
        )

    }


}
