package com.mukesh.template.fcmHandler

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mukesh.template.ui.views.MainActivity
import com.mukesh.template.R
import com.mukesh.template.utils.ID_ALIAS
import kotlinx.parcelize.Parcelize


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseNotification : FirebaseMessagingService() {

    /**
     * Initialize Variables
     * */
    private val notificationData by lazy { NotificationData() }


    /**
     * On Message Received
     * */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification.let {
            notificationData.title = it?.title ?: getString(R.string.app_name)
            notificationData.message = it?.body ?: getString(R.string.app_name)
        }

        if (remoteMessage.data.isNotEmpty()){
            remoteMessage.data.let { data ->

                notificationData.title = data["title"] ?: getString(R.string.app_name)

                notificationData.message = data["body"] ?: getString(R.string.app_name)

                notificationData.orderId = data["orderId"] ?: ""

            }
        }

        sendNotification()

    }



    /**
     * Send Notifications
     * */
    private fun sendNotification() {

        val notificationBuilder = NotificationCompat.Builder(this, packageName)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(notificationData.title ?: "")
            .setContentText(notificationData.message)
            .setAutoCancel(true)
            .setSilent(false)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(getPendingIntent(destinationId = ID_ALIAS.rv))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(packageName, notificationData.title, NotificationManager.IMPORTANCE_HIGH).apply {
                description = notificationData.message
                setBypassDnd(true)
                enableVibration(true)
                setShowBadge(true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setAllowBubbles(true)
                }
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }



    /**
     * Pending Intent Handle
     * */
    private fun getPendingIntent(destinationId: Int, bundle: Bundle? = null): PendingIntent =
        if(Build.VERSION.SDK_INT >= 31)
            NavDeepLinkBuilder(this)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.template)
                .setDestination(destinationId)
                .setArguments(bundle)
                .createTaskStackBuilder()
                .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)!!
        else
            NavDeepLinkBuilder(this)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.template)
                .setDestination(destinationId)
                .setArguments(bundle)
                .createPendingIntent()


    /**
     * Notification Data
     * */
    @Parcelize
    data class NotificationData(
        var title: String? = "",
        var message: String? = "",
        var orderId: String? = "",
    ) : Parcelable

}
