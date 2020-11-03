package com.otb.githubissues.network

import com.otb.githubissues.common.NetworkStateChecker
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Mohit Rajput on 3/11/2020.
 */

class NetworkReachabilityInterceptor(private val networkStateChecker: NetworkStateChecker) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkStateChecker.isNetworkAvailable()) {
            throw NetworkAvailabilityException("Network not available")
        }
        val request = chain.request()
        return chain.proceed(request)
    }

}

class NetworkAvailabilityException(message: String) : Exception(message)