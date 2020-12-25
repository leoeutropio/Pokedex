package com.leoeutropio.pokedex.retrofit.utils

import android.content.Context;
import android.net.ConnectivityManager;


object NetworkUtil {
    var TYPE_WIFI = 1
    var TYPE_MOBILE = 2
    var TYPE_NOT_CONNECTED = 0
    fun getConnectivityStatus(context: Context): Int {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun isConnected(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return true
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return true
        }
        return false
    }

    fun getConnectivityStatusString(context: Context): String? {
        val conn = getConnectivityStatus(context)
        var status: String? = null
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled"
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled"
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet"
        }
        return status
    }
}