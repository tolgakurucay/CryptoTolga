package com.tolgakurucay.cryptotolga.model

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "f87891ad-21f1-4895-81db-7397bc31f4c7"

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()


        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

    }
}