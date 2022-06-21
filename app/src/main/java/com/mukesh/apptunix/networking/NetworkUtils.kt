package com.mukesh.apptunix.networking

import android.webkit.MimeTypeMap
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File


/**
 * Get JSON Request body
 * */
fun String.getJsonRequestBody() = this.toRequestBody("application/json".toMediaTypeOrNull())


/**
 * Get Form Data Body
 * */
fun String.getFormDataBody() = this.toRequestBody("multipart/form-data".toMediaTypeOrNull())


/**
 * Get Part Map
 * */
fun File.getPartMap(key: String): MultipartBody.Part {
    val reqFile = this.asRequestBody(this.absolutePath.getMimeType().toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(key, this.name, reqFile)
}


/**
 * Convert any data into json data
 * */
fun Any.convertGsonString(): String = Gson().toJson(this)


/**
 * Convert String into Data Class
 * */
inline fun <reified T> String.convertStringIntoClass(): T = Gson().fromJson(this, T::class.java)


/**
 * Get Mime Type ......
 * */
fun String.getMimeType(): String {
    return try {
        var mimeType = MimeTypeMap.getFileExtensionFromUrl(this.replace(" ",""))
        if (mimeType.isNullOrEmpty())
            mimeType = this.replace(" ","").substring(this.replace(" ","").lastIndexOf(".")).replace(".","")
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeType) ?: "image/jpg"
    } catch (e: Exception) {
        "image/jpg"
    }
}


/**
 * Handle Error Messages
 * */
fun <T> NetworkState<T>.getErrorMessage() : String = when (this.error) {
    is Throwable -> this.error.message.getResponseError()
    else -> this.error.toString().getResponseError()
}


/**
 * Get Response error
 * */
fun String?.getResponseError(): String{
    if (this.isNullOrEmpty()) return ""
    return try {
        val jsonObject = JSONObject(this)
        if (jsonObject.has("message")){
            jsonObject.getString("message")
        }else
            this
    }catch (e:Exception){
        this
    }
}
