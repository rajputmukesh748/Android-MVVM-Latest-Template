package com.mukesh.apptunix.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mukesh.apptunix.BuildConfig
import com.mukesh.apptunix.R
import java.util.*


/**
 * Set Animation on any view
 */
fun View.loadAnimation(duration: Long, animationType: Int) {
    try {
        val animation = AnimationUtils.loadAnimation(context, animationType)
        animation.duration = duration
        startAnimation(animation)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/** -------- HIDE KEYBOARD -------- */
@SuppressLint("ServiceCast")
fun Activity.hideSoftKeyBoard() {
    try {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * Show Snack Bar
 * */
fun Activity.showSnackBar(string: String) = try {
    var message = string
    if (message.contains("Unable to resolve host"))
        message = getString(R.string.internet_not_connected)
    Snackbar.make(
        findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG
    ).apply {
        setBackgroundTint(ContextCompat.getColor(this@showSnackBar, R.color.white))
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
        setTextColor(ContextCompat.getColor(this@showSnackBar, R.color.white))
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
        show()
    }
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * First Letter Capitalize
 * */
fun String.firstLetterCapitalize() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }


/**
 * Convert into format
 */
fun String?.convertFormat(format : String = "%.2f"): String{
    return if (this.isNullOrEmpty()) "0.0"
    else String.format(format, this.toDoubleOrNull() ?: 0.0)
}


/**
 * Load Image
 * */
@SuppressLint("CheckResult")
fun AppCompatImageView.loadImage(
    url: String,
    errorPlaceHolder: Int = R.drawable.ic_launcher_background
) = try {
    val circularProgressDrawable = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    val requestOptions = RequestOptions().apply {
        placeholder(circularProgressDrawable)
        error(errorPlaceHolder)
        skipMemoryCache(false)
        sizeMultiplier(1f)
        centerCrop()
    }

    Glide.with(this)
        .load(BuildConfig.BASE_URL.plus(url))
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(errorPlaceHolder)
        .into(this)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Check Permissions
 * */
fun Context.checkPermissions(vararg permission: String, returnData: (Boolean) -> Unit) = try {
    Dexter.withContext(this)
        .withPermissions(*permission)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if(report.areAllPermissionsGranted()){
                        returnData(true)
                    }else if (report.isAnyPermissionPermanentlyDenied){
                        AlertDialog.Builder(this@checkPermissions).apply {
                            setTitle(STRING_ALIAS.app_name)
                            setMessage("App need's permission. Please grant permission's.")
                            setPositiveButton("Grant") { _, _ ->
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                    startActivity(this)
                                }
                            }
                            show()
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        })
        .check()
}catch (e:Exception){
    e.printStackTrace()
}


/**
 * Get Address from lat lng
 * */
fun Context.getAddress(latLng: LatLng): String = try {
    val geocoder: Geocoder = Geocoder(this)
    val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    if (addresses.isNotEmpty()) addresses[0].getAddressLine(0) ?: ""
    else ""
}catch (e:Exception){
    e.printStackTrace()
    ""
}


/**
 * Get Auth Token
 * */
fun getFCMToken(fcmToken : (String) -> Unit = {}) = try {
    FirebaseMessaging.getInstance().token.addOnCompleteListener{ task ->
        if (!task.isSuccessful) {
            return@addOnCompleteListener
        }
        fcmToken(task.result)
    }
}catch (e:Exception){
    e.printStackTrace()
}