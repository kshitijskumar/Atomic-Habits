package com.example.atomichabits.data.api

import com.example.atomichabits.data.models.LoginModel
import com.example.atomichabits.data.models.SignupModel
import com.example.atomichabits.data.response.ActivityResponse
import com.example.atomichabits.data.response.LoginResponse
import com.example.atomichabits.data.response.PostResponse
import com.example.atomichabits.data.response.UserResponse
import com.example.atomichabits.utils.Constants.BASE_URL
import com.example.atomichabits.utils.Injector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {


    @POST("users/register")
    suspend fun signupNewsUser(@Body user: SignupModel) : Response<Any>

    @POST("users/login")
    suspend fun loginExistingUser(@Body user: LoginModel) : Response<LoginResponse>

    @GET("activity")
    suspend fun getARandomActivity(
        @Header("x-auth-token") token: String? = Injector.getInjector().getTokenForUser()
    ) : Response<ActivityResponse>

    @GET("users/{userId} ")
    suspend fun getUserDetails(
        @Header("x-auth-token") token: String? = Injector.getInjector().getTokenForUser(),
        @Path("userId") id: String? = Injector.getInjector().getIdForUser()
    ) : Response<UserResponse>

    @GET("feed")
    suspend fun getAllPosts(
        @Header("x-auth-token") token: String? = Injector.getInjector().getTokenForUser()
    ) : Response<List<PostResponse>>

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