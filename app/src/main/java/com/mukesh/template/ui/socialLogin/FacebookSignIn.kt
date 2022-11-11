package com.mukesh.template.ui.socialLogin

import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mukesh.template.networking.convertGsonString
import com.mukesh.template.utils.showSnackBar
import org.json.JSONObject

/**
 * Facebook SignIn Methods
 * */
object FacebookSignIn {

    private val callbackManager by lazy { CallbackManager.Factory.create() }
    private val firebaseAuth by lazy { Firebase.auth }


    /**
     * Initialize Facebook Login
     * */
    fun initializeFacebookLogin(
        fragment: Fragment,
        socialLoginData: (SocialLoginData) -> Unit = {}
    ) = try {
        LoginManager.getInstance()
            .logInWithReadPermissions(fragment, callbackManager, listOf("email,public_profile"))
//        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_ONLY
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    logoutFacebook()
                }

                override fun onError(error: FacebookException) {
                    AccessToken.setCurrentAccessToken(null)
                    logoutFacebook()
                }

                override fun onSuccess(result: LoginResult) {
                    try {
                        val credential =
                            FacebookAuthProvider.getCredential(result.accessToken.token)
                        firebaseAuth.signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    task.result.additionalUserInfo?.profile?.let { mapData ->
                                        SocialLoginData().apply {
                                            firstName = mapData["first_name"].toString()
                                            lastName = mapData["last_name"].toString()
                                            email = mapData["email"].toString()
                                            facebookId = mapData["id"].toString()
                                            if (mapData.containsKey("picture")) {
                                                JSONObject(
                                                    mapData["picture"]?.convertGsonString() ?: ""
                                                ).apply {
                                                    if (has("data"))
                                                        getJSONObject("data").apply {
                                                            if (has("url"))
                                                                image = getString("url")
                                                        }
                                                }
                                            }
                                            socialLoginData(this)
                                        }
                                    }
                                } else {
                                    fragment.requireActivity()
                                        .showSnackBar(task.exception?.localizedMessage ?: "")
                                }
                                logoutFacebook()
                            }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            })
    } catch (e: Exception) {
        e.printStackTrace()
    }


    /**
     * Facebook Logout
     * */
    private fun logoutFacebook() = try {
        LoginManager.getInstance().logOut()
        Firebase.auth.signOut()
    } catch (e: Exception) {
        e.printStackTrace()
    }


}