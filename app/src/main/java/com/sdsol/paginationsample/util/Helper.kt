package com.sdsol.paginationsample.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("MissingPermission")
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

suspend inline fun CoroutineScope.toMain(crossinline scope: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main) { scope.invoke(this) }
}

fun View.showSnackBar(message: String) {
    try {
        Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_LONG
        )
            .apply {
                this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    ?.apply {
                        maxLines = 5
                        isSingleLine = false
                    }
            }
            .show()
    } catch (e: Exception) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}