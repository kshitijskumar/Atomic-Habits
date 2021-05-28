package com.example.atomichabits.data.api

import com.example.atomichabits.data.models.LoginModel
import com.example.atomichabits.data.models.SignupModel
import com.example.atomichabits.data.response.LoginResponse
import com.example.atomichabits.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("api/users")
    suspend fun signupNewsUser(@Body user: SignupModel) : Response<Any>

    @POST("api/auth")
    suspend fun loginExistingUser(@Body user: LoginModel) : Response<LoginResponse>

    companion object {
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        private val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun apiService() : ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}