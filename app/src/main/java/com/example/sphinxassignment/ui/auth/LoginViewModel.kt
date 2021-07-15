package com.example.sphinxassignment.ui.auth

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sphinxassignment.network.responses.LoginResponse
import com.example.sphinxassignment.Utils

import com.example.sphinxassignment.network.api.repository.ApiRepository
import com.example.sphinxassignment.network.responses.CityList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    @ApplicationContext
    val context: Context,
    private val apiRepository: ApiRepository,
) : ViewModel() {

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    var cityList: MutableLiveData<List<CityList>> = MutableLiveData()

    fun doSignIn(
        emailID: String,
        pass: String
    ) {
        viewModelScope.launch {

            val loginResponse = LoginResponse().apply {
                email = emailID
                password = pass
            }

            apiRepository.authenticate(loginResponse).let {
                try {
                    if (it.isSuccessful && it.body()?.status.equals("success")) {
                        isSuccessful.value = true
                    } else {
                        if (it.errorBody() != null) {
                            Utils.showToast(
                                context,
                                it.errorBody()?.string()
                                    ?.split(",")!![1].removeSuffix("}")
                            )
                        }
                        isSuccessful.value = false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isSuccessful.value = false
                }
            }
        }
    }

    fun doSignUp(
        emailId: String,
        userName: String,
        mobile: String,
        user_Type: String,
        add: String,
        lat: String,
        lon: String,
        pass: String,
        so_id: String,
        so_type: String,
        cityId: Int,
    ) {
        viewModelScope.launch {

            val loginResponse = LoginResponse().apply {
                email = emailId
                username = userName
                contact = mobile
                user_type = user_Type
                address = add
                latitude = lat.toDouble()
                longitude = lon.toDouble()
                password = pass
                social_id = so_id
                social_type = so_type
                city_id = cityId
            }

            apiRepository.signUp(loginResponse).let {
                try {
                    if (it.isSuccessful && it.code() == 200) {
                        isSuccessful.value = true
                        Utils.showToast(context, "User Registered Successfully .")
                    } else {
                        if (it.errorBody() != null) {
                            Utils.showToast(
                                context,
                                it.errorBody()?.string()
                                    ?.split(",")!![1].removeSuffix("}")
                            )
                        }
                        isSuccessful.value = false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isSuccessful.value = false
                }
            }
        }
    }

    fun getCityList() {
        viewModelScope.launch {

            apiRepository.getCityList().let {
                try {
                    if (it.isSuccessful && it.body()?.status.equals("success")) {

                        if (it.body()?.getCityList?.isNotEmpty() == true) {
                            cityList.value = it.body()?.getCityList
                        }
                    } else {
                        if (it.errorBody() != null) {
                            Utils.showToast(
                                context,
                                it.errorBody()?.string()
                                    ?.split(",")!![1].removeSuffix("}")
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isSuccessful.value = false
                }
            }
        }
    }

}