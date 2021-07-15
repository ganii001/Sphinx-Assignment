package com.example.sphinxassignment.network.api.apihelper


import com.example.sphinxassignment.network.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body

interface ApiHelper {

    suspend fun authenticate(@Body loginResponse: LoginResponse): Response<LoginResponse>

    suspend fun signUp(@Body loginResponse: LoginResponse): Response<LoginResponse>

    suspend fun getCityList(): Response<LoginResponse>

}