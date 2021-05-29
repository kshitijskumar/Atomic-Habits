package com.example.atomichabits.data.api

import com.example.atomichabits.data.models.LoginModel
import com.example.atomichabits.data.models.SignupModel
import com.example.atomichabits.utils.Injector
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiServiceTest {

    private lateinit var api: ApiService
    private val headerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwYjFlNTRjMTdjNjgwMDAxNTg4MmUzMiIsImlhdCI6MTYyMjI3MTcyNywiZXhwIjoxNjI0ODYzNzI3fQ.UtgjmzW5IJo2lJYjB57KOBFJHWAFDdeYphY2VWBTdB8"

    @Before
    fun setup() {
        api = Injector.getInjector().provideApiService()
    }

    @Test
    fun signupNewUser_onSuccess_returnsTrue() = runBlocking {
        val user = SignupModel("Kshitij", "kshitij2212@gmail.com", "chalrha")

        val response = api.signupNewsUser(user)

        Assert.assertTrue(response.isSuccessful)
    }

    @Test
    fun loginUser_onSuccess_returnsToken() = runBlocking {
        val user = LoginModel("kshitij2212@gmail.com", "chalrha")

        val response = api.loginExistingUser(user)

        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getActivity_onSuccess_returnsAnActivity() = runBlocking {
        val user = LoginModel("kshitij2212@gmail.com", "chalrha")

        var token: String? = null
        val response = api.loginExistingUser(user)
        if(response.isSuccessful) {
            token = response.body()?.token
        }

        val resp = api.getARandomActivity(token)
        Assert.assertNotNull(resp)
    }

    @Test
    fun getUserDetails_onSuccess_returnsUser() = runBlocking {
        val response = api.getUserDetails(headerToken)

        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getFeed_onSuccess_returnsListOfPosts() = runBlocking {
        val response = api.getAllPosts(headerToken)

        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

}