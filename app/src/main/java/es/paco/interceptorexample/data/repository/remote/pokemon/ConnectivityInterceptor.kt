package es.paco.interceptorexample.data.repository.remote.pokemon

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import es.paco.interceptorexample.extension.TAG
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val cManager: ConnectivityManager, private val messageError: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isInternetAvailable()) {
            Log.d(TAG, "l> Error de red")
            throw NoConnectivityException(messageError)
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cManager.activeNetwork ?: return false
            val actNw =
                cManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            cManager.run {
                cManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    class NoConnectivityException(private val messageError: String) : IOException() {
        @Override
        override fun getLocalizedMessage(): String {
            return messageError
        }
    }
}