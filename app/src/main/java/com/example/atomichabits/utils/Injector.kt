package com.example.atomichabits.utils

import com.example.atomichabits.data.api.ApiService
import com.example.atomichabits.data.repository.AuthRepository
import com.example.atomichabits.data.repository.UserRepository

class Injector private constructor() {

    private var apiService: ApiService? = null

    //repositories
    private var authRepo: AuthRepository? = null
    private var userRepo: UserRepository? = null

    private var token: String? = null

    fun storeTokenForUser(token: String) {
        this.token = token
    }

    fun removeTokenForUser() {
        token = null
    }

    fun getTokenForUser() = token

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

    fun provideUserRepo() : UserRepository {
        if(userRepo == null) {
            userRepo = UserRepository()
        }

        return userRepo!!
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