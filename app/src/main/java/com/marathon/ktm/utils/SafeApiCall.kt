package com.marathon.ktm.utils

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline call: suspend () -> T
): RemoteResult<T> {
    return try {
        val remoteResult = withContext(dispatcher) { call() }
        RemoteResult.Success(remoteResult)
    } catch (e: Exception) {
        Log.e("apicall", e.message ?: "network error")
        RemoteResult.Error(e)
    }
}