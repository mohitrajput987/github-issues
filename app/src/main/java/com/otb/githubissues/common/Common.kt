package com.otb.githubissues.common

/**
 * Created by Mohit Rajput on 3/11/20.
 */

sealed class ProgressStatus {
    object Loading : ProgressStatus()

    object Success : ProgressStatus()

    class Error(val errorMessage: String) : ProgressStatus()
}

sealed class DataResult<T> {
    class DataSuccess<T>(val data: T) : DataResult<T>()
    class DataError<T>(val errorMessage: String) : DataResult<T>()
}