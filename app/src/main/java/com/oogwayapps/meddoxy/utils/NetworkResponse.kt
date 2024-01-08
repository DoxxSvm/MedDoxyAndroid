package com.oogwayapps.meddoxy.utils

sealed class NetworkResponse<T> {

    class Success<T>(val response:T):NetworkResponse<T>()
    class Error<T>(val message:String?=null):NetworkResponse<T>()
}