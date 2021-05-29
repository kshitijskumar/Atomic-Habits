package com.example.atomichabits.data.repository

import com.example.atomichabits.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseRepository {

    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow<Resource<T>> {
        emit(Resource.Loading)

        val response = call.invoke()

        if (response.isSuccessful) {
            if(response.body() != null) {
                emit(Resource.Success(response.body()!!))
            }else {
                emit(Resource.Error("Api called successfully but no response in return."))
            }
        }else {
            emit(Resource.Error("Something unexpected happened."))
        }

    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        }
}