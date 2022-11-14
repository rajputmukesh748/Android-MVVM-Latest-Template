package com.mukesh.template.commonClasses.connectivityManager


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.mukesh.template.utils.ioThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory


/**
 * Connectivity Manager Handler With Live Data
 * */
class ConnectivityManager(context: Context) : LiveData<Boolean>() {

    /**
     * Network Call Back
     * */
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()


    /**
     * Initializer
     * */
    init {
        ioThread {
            delay(500)
            withContext(Dispatchers.Main) {
                this@ConnectivityManager.checkValidNetworks()
            }
        }
    }


    /**
     * Check Valid Network
     * */
    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }


    /**
     * On Active Network
     * */
    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }


    /**
     * On In Active Network
     * */
    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }


    /**
     * Create Network Call back
     * */
    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        /**
         * Available Network Call Back
         * */
        override fun onAvailable(network: Network) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)

            if (hasInternetCapability == true) {
                // Check if this network actually has internet
                ioThread {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }


        /**
         * On Lost Network Call back
         * */
        override fun onLost(network: Network) {
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }


    /**
     * Check Internet Connection is Active Or Not
     * */
    object DoesNetworkHaveInternet {
        /**
         * Check Network Initializer
         * */
        fun execute(socketFactory: SocketFactory): Boolean {
            return try {
                Log.d(TAG, "PINGING Google...")
                val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                socket.close()
                true
            } catch (e: IOException) {
                false
            }
        }
    }

}