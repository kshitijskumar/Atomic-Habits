package com.example.atomichabits.data.repository

import com.example.atomichabits.data.api.ApiService
import com.example.atomichabits.data.models.LoginModel
import com.example.atomichabits.data.models.SignupModel
import com.example.atomichabits.utils.Injector

class AuthRepository(
    private val api: ApiService = Injector.getInjector().provideApiService()
) : BaseRepository() {

    suspend fun signupNewUser(user: SignupModel) = safeApiCall {
        api.signupNewsUser(user)
    }

    suspend fun loginExistingUser(user: LoginModel) = safeApiCall {
        api.loginExistingUser(user)
    }
}