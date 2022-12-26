package com.mukesh.template.controller

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.mukesh.easydatastoremethods.calldatastore.CallDataStore
import com.mukesh.template.socketSetUp.SocketSetup
import com.mukesh.template.utils.mainThread
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

/**
 * Controller Class
 * */
@HiltAndroidApp
class Controller : Application(), Application.ActivityLifecycleCallbacks,
    Thread.UncaughtExceptionHandler {


    companion object {
        @JvmStatic
        var context: WeakReference<Context>? = null
    }


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
        registerActivityLifecycleCallbacks(this)
//        FacebookSdk.fullyInitialize()
//        AppEventsLogger.activateApp(this)
//        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    /**
     * Application Lifecycle Callback's
     * */
    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        context = WeakReference(activity)
    }
    override fun onActivityStarted(p0: Activity) = Unit
    override fun onActivityResumed(activity: Activity) {
        context = WeakReference(activity)
    }
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