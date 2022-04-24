package com.anuj

import android.app.Application
import com.google.android.gms.ads.MobileAds

class BMIApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(applicationContext)
    }
}