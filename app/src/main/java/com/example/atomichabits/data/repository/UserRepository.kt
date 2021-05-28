package com.example.atomichabits.data.repository

import com.example.atomichabits.data.api.ApiService
import com.example.atomichabits.utils.Injector
import com.example.atomichabits.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val api: ApiService = Injector.getInjector().provideApiService()
) : BaseRepository() {

    suspend fun getARandomActivity() = safeApiCall {
        api.getARandomActivity()
    }

    //demo upload function
    suspend fun uploadActivity() = flow<Resource<Any>>{
        emit(Resource.Loading)
        delay(1500L)
        emit(Resource.Success("Success"))
    }
}