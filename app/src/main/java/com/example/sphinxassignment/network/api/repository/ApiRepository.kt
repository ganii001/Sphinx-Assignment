package com.example.sphinxassignment.network.api.repository


import com.example.sphinxassignment.network.responses.LoginResponse
import com.example.sphinxassignment.network.api.apihelper.ApiHelper
import retrofit2.http.Body
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun authenticate(@Body loginResponse: LoginResponse) =
        apiHelper.authenticate(loginResponse)

    suspend fun signUp(@Body loginResponse: LoginResponse) = apiHelper.signUp(loginResponse)

    suspend fun getCityList() = apiHelper.getCityList()

}