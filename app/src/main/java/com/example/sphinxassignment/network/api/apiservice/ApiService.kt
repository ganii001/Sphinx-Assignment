package com.example.sphinxassignment.network.api.apiservice

import com.example.sphinxassignment.network.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @POST("login-user")
    suspend fun authenticate(
        @Body loginResponse: LoginResponse
    ): Response<LoginResponse>

    @POST("signup")
    suspend fun signUp(
        @Body loginResponse: LoginResponse
    ): Response<LoginResponse>

    @GET("city-list")
    suspend fun getCityList(): Response<LoginResponse>

}