package com.angelhack.thanosgo

import android.app.Application
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService
import android.content.Intent



class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        applicationContext.startService(Intent(applicationContext, TransferService::class.java))

    }
}