package com.rsamqui.bakingbills.api.network

import com.rsamqui.bakingbills.api.ApiService

object Common {
    private val BASE_URL = "https://bakingbills.herokuapp.com/"
    val retrofitService: ApiService
    get() = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
}