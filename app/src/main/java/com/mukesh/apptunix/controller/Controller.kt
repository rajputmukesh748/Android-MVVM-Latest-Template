package com.mukesh.apptunix.controller

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.mukesh.apptunix.socketSetUp.SocketSetup
import com.mukesh.apptunix.utils.mainThread
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import dagger.hilt.android.HiltAndroidApp

/**
 * Controller Class
 * */
@HiltAndroidApp
class Controller : Application(), Application.ActivityLifecycleCallbacks,
    Thread.UncaughtExceptionHandler {

    /**
     * On Create Method
     * */
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FontsOverride.setDefaultFont(this, "SERIF", "app_fonts.ttf")
        CallDataStore.initializeDataStore(
            context = applicationContext,
            dataBaseName = applicationContext.packageName
        )
        this.registerActivityLifecycleCallbacks(this)
//        FacebookSdk.fullyInitialize()
//        AppEventsLogger.activateApp(this)
//        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    /**
     * Application Lifecycle Callback's
     * */
    override fun onActivityCreated(p0: Activity, p1: Bundle?) = Unit
    override fun onActivityStarted(p0: Activity) = Unit
    override fun onActivityResumed(p0: Activity) = Unit
    override fun onActivityPaused(p0: Activity) = Unit
    override fun onActivityStopped(p0: Activity) = Unit
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit
    override fun onActivityDestroyed(p0: Activity) = Unit


    /**
     * Un Caught Exception Handler
     * */
    override fun uncaughtException(p0: Thread, p1: Throwable) {
        mainThread {
            //Handle App Crash or Generate a custom report
        }
    }


    /**
     * On Terminate Method
     * */
    override fun onTerminate() {
        SocketSetup.disconnectSocket()
        super.onTerminate()
    }

}