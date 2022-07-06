package com.example.weather4u.util

//response check success or error or loading can use in any calling 7 video
sealed class Resource<T>(
    val data:T?=null,
    val message:String?=null
) {
    class Success<T>(data:T): Resource<T>(data)
    class Error<T>(message: String,data: T?=null): Resource<T>(data, message)
    class Loading<T> : Resource<T>()

}