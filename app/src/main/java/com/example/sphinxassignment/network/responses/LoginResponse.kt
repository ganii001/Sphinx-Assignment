package com.example.sphinxassignment.network.responses


import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("access_token")
    var access_token: String? = null,

    var status: String? = null,

    var email: String? = null,

    var password: String? = null,

    @field:SerializedName("social_id")
    var social_id: String? = null,

    @field:SerializedName("user_type")
    var user_type: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("social_type")
    var social_type: String? = null,

    @field:SerializedName("contact")
    var contact: String? = null,

    var foodname: String? = null,

    @field:SerializedName("latitude")
    var latitude: Double? = null,

    @field:SerializedName("username")
    var username: String? = null,

    @field:SerializedName("longitude")
    var longitude: Double? = null,

    @field:SerializedName("city_id")
    var city_id: Int? = null,

    @field:SerializedName("list")
    var getCityList: List<CityList>? = null,
)

data class CityList(

    @field:SerializedName("latitude")
    var latitude: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("country_id")
    var countryId: Int? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("longitude")
    var longitude: String? = null
)