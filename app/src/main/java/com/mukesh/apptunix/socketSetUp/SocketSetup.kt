package com.mukesh.apptunix.socketSetUp

import android.util.Log
import com.mukesh.apptunix.BuildConfig
import com.mukesh.apptunix.dataStore.getAuthToken
import com.mukesh.apptunix.networking.convertStringIntoClass
import com.mukesh.apptunix.utils.defaultThread
import com.mukesh.apptunix.utils.ioThread
import com.mukesh.apptunix.utils.mainThread
import com.mukesh.apptunix.utils.unconfinedThread
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject


/**
 * Socket SetUp
 * */
object SocketSetup {

    private var socket: Socket? = null
    private var socketInterface: SocketInterface? = null


    /**
     * Initialize Socket Interface
     * */
    fun initializeInterface(socketInterface: SocketInterface){
        SocketSetup.socketInterface = socketInterface
    }


    /**
     * For Connect Socket
     * */
    fun connectSocket() {
        try {
            if (socket == null){
                getAuthToken { authToken ->
                    val ioOptions = IO.Options().apply {
                        forceNew = true
                        reconnectionAttempts = Integer.MAX_VALUE
                        timeout = 10000
                        query = "token=$authToken"
                    }
                    socket = IO.socket(BuildConfig.BASE_URL, ioOptions)
                    listenerOn(Socket.EVENT_CONNECT, SOCKET_CONNECT)
                    listenerOn(Socket.EVENT_DISCONNECT, SOCKET_DISCONNECT)
                    listenerOn(Socket.EVENT_CONNECT_ERROR, SOCKET_ERROR)
                    listenerOn(JOIN_ORDER_ROOM, joinOrderRoomListener)
                    listenerOn(UPDATE_ORDER_STATUS, changeOrderStatusListener)
                    if (socket?.connected() == false)
                        socket?.connect()
                }
            }else{
                if (socket?.connected() == false)
                    socket?.connect()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * For Disconnect Socket
     * */
    fun disconnectSocket() {
        if (socket?.connected() == true){
            listenerOff(Socket.EVENT_CONNECT, SOCKET_CONNECT)
            listenerOff(Socket.EVENT_DISCONNECT, SOCKET_DISCONNECT)
            listenerOff(Socket.EVENT_CONNECT_ERROR, SOCKET_ERROR)
            listenerOff(JOIN_ORDER_ROOM, joinOrderRoomListener)
            listenerOff(UPDATE_ORDER_STATUS, changeOrderStatusListener)
            socket?.disconnect()
            socket = null
        }
    }


    private val SOCKET_CONNECT = Emitter.Listener {
        Log.e("SocketPrint","Socket Connected")
    }


    private val SOCKET_DISCONNECT = Emitter.Listener {
        Log.e("SocketPrint","Socket Disconnected")
    }


    private val SOCKET_ERROR = Emitter.Listener {
        Log.e("SocketPrint","Socket Error   ${it[0]}")
    }


    /**
     * Join Room Order Listener
     * */
    private val joinOrderRoomListener = Emitter.Listener {
        mainThread {
            Log.e("RoomJoinMessage","=====>  ${it[0]}")
        }
    }


    /**
     * Change Order Status Listener
     * */
    private val changeOrderStatusListener = Emitter.Listener {
        mainThread {
            Log.e("OrderStatus", "====>  ${it[0]}")
        }
    }


    /**
     * Listener On
     * */
    private fun listenerOn(key: String, listener: Emitter.Listener) {
        try {
            if (socket?.hasListeners(key) == false)
                socket?.on(key, listener)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * Listener Off
     * */
    private fun listenerOff(key: String, listener: Emitter.Listener) {
        try {
            if (socket?.hasListeners(key) == true)
                socket?.off(key, listener)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * Join Order Room
     * */
    fun joinRoomOrder(orderId: String){
        try {
            emit(JOIN_ORDER_ROOM, JSONObject().apply {
                put("orderId", orderId)
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * Update Order Status
     * */
    fun updateOrderStatus(orderId: String, orderStatus: String) {
        try {
            emit(UPDATE_ORDER_STATUS, JSONObject().apply {
                put("orderId", orderId)
                put("orderStatus", orderStatus)
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * Emit Socket
     * */
    private fun <T> emit(key: String, data: T){
        try {
            if (socket == null && socket?.connected() == false)
                connectSocket()
            socket?.emit(key, data)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}
