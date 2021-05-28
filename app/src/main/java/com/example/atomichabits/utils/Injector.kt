package com.example.atomichabits.utils

import com.example.atomichabits.data.api.ApiService

class Injector private constructor() {

    private var apiService: ApiService? = null

    fun provideApiService() : ApiService {
        if(apiService == null) {
            apiService = ApiService.apiService()
        }

        return apiService!!
    }



    companion object {
        var instance: Injector? = null

        fun getInjector() : Injector {
            if(instance == null) {
                instance = Injector()
            }

            return instance!!
        }

    }
}