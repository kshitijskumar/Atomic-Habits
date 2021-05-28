package com.example.atomichabits.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Error(val errorMsg: String) : Resource<Nothing>()
}