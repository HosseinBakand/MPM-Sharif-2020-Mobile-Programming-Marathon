package com.marathon.ktm.utils

sealed class RemoteResult<out R> {
    data class Success<out T>(val data: T) : RemoteResult<T>()
    data class Error(val exception: Exception) : RemoteResult<Nothing>()
    object Loading : RemoteResult<Nothing>()
}