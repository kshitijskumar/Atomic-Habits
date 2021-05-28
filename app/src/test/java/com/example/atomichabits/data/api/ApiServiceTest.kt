package com.example.atomichabits.data.api

import com.example.atomichabits.utils.Injector
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiServiceTest {

    private lateinit var api: ApiService

    @Before
    fun setup() {
        api = Injector.getInstance().provideApiService()
    }
}