package com.mukesh.template.socketSetUp

/**
 * Socket Emitter's and Listener's
 * */
const val JOIN_ORDER_ROOM = "joinOrderRoom"

const val UPDATE_ORDER_STATUS = "updateOrderStatus"


/**
 * Interface for Handle socket data
 * */
interface SocketInterface{
    fun updateOrderStatus(orderStatus: String) {}
}
