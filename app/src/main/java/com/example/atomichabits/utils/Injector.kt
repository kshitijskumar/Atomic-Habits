package com.example.atomichabits.utils

import com.example.atomichabits.data.api.ApiService
import com.example.atomichabits.data.repository.AuthRepository

class Injector private constructor() {

    private var apiService: ApiService? = null

    //repositories
    private var authRepo: AuthRepository? = null

    fun provideApiService() : ApiService {
        if(apiService == null) {
            apiService = ApiService.apiService()
        }

        return apiService!!
    }

    fun provideAuthRepo() : AuthRepository {
        if(authRepo == null) {
            authRepo = AuthRepository()
        }

        return authRepo!!
    }


    companion object {
        private var instance: Injector? = null

        fun getInjector() : Injector {
            if(instance == null) {
                instance = Injector()
            }

            return instance!!
        }

    }
}