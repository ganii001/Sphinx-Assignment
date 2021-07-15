package com.example.sphinxassignment.network.api.apihelperimpl

import com.example.sphinxassignment.network.responses.LoginResponse
import com.example.sphinxassignment.network.api.apihelper.ApiHelper
import com.example.sphinxassignment.network.api.apiservice.ApiService
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) :
    ApiHelper {

    override suspend fun authenticate(@Body loginResponse: LoginResponse): Response<LoginResponse> =
        apiService.authenticate(loginResponse)

    override suspend fun signUp(@Body loginResponse: LoginResponse): Response<LoginResponse> =
        apiService.signUp(loginResponse)

    override suspend fun getCityList(): Response<LoginResponse> =
        apiService.getCityList()

}