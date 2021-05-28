package com.example.atomichabits.data.repository

import com.example.atomichabits.data.api.ApiService
import com.example.atomichabits.utils.Injector

class UserRepository(
    private val api: ApiService = Injector.getInjector().provideApiService()
) : BaseRepository() {

    suspend fun getARandomActivity() = safeApiCall {
        api.getARandomActivity()
    }
}