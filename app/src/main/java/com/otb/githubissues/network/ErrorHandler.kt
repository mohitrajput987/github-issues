package com.otb.githubissues.network

import retrofit2.Response

/**
 * Created by Mohit Rajput on 3/11/20.
 */
class ErrorHandler {
    companion object {
        fun getError(response: Response<out Any>): String {
            val errorBody = response.errorBody()?.string()
            return errorBody ?: "something went wrong"
        }
    }
}