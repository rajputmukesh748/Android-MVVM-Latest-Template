package com.mukesh.apptunix.ui.socialLogin

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mukesh.apptunix.R
import java.lang.ref.WeakReference

/**
 * Google SignIn Client
 * */
object GoogleSignIn {

    private val firebaseAuth by lazy { Firebase.auth }
    private lateinit var googleSignInClient: WeakReference<GoogleSignInClient>

    /**
     * Initialize Google Sign In Client
     * */
    fun initializeGoogleSignIn(context : Context, resultLauncher: ActivityResultLauncher<Intent>) = try {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("") //context.getString(R.string.default_web_client_id)
            .requestEmail()
            .build()
        googleSignInClient = WeakReference(GoogleSignIn.getClient(context,googleSignInOptions))
        resultLauncher.launch(googleSignInClient.get()?.signInIntent)
    }catch (e:Exception){
        e.printStackTrace()
    }


    /**
     * Handle Activity Result
     * */
    fun handleActivityResult(data: Intent?, socialLoginData: (SocialLoginData) -> Unit = {}) = try {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){
                task.result.additionalUserInfo?.profile?.let { mapData ->
                    SocialLoginData().apply {
                        firstName = mapData["given_name"].toString()
                        lastName = mapData["family_name"].toString()
                        email = mapData["email"].toString()
                        googleId = mapData["sub"].toString()
                        image = mapData["picture"].toString()
                        socialLoginData(this)
                    }
                }
            }else{
                println(task.exception?.localizedMessage ?: "")
            }
            Firebase.auth.signOut()
            googleSignInClient.get()?.signOut()
        }
    }catch (e:Exception){
        e.printStackTrace()
    }

}