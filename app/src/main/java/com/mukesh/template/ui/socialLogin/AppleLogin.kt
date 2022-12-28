package com.mukesh.template.ui.socialLogin

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider

/**
 * Apple Login
 * */
object AppleLogin {

    /**
     * OAuth and Firebase Providers
     * */
    private val provider by lazy { OAuthProvider.newBuilder("apple.com") }
    private val auth by lazy { FirebaseAuth.getInstance() }


    /**
     * Builder
     * */
    fun builder(activity: Activity) {
        val pending = auth.pendingAuthResult
        if (pending == null) {
            pendingAuthResult()
        } else {
            signInProvider(activity, provider)
        }
    }


    /**
     * Pending Auth Result
     * */
    private fun pendingAuthResult() = try {
        auth.pendingAuthResult?.addOnSuccessListener { authResult ->
            getUserData(authResult.user)
        }?.addOnFailureListener {
            println("Error occur is $it")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


    /**
     * Sign In Provider
     * */
    private fun signInProvider(activity: Activity, provider: OAuthProvider.Builder) = try {
        auth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                getUserData(authResult.user)
            }.addOnFailureListener {
                println("Error occur is $it")
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }


    /**
     * Get User Data
     * */
    private fun getUserData(userData: FirebaseUser?) {
        userData?.let {
            println("User login data ---> name is ${userData.displayName} and email address is ${userData.email}  and phone number is ${userData.phoneNumber}  and phone number is ${userData.photoUrl}")
        }
    }

}