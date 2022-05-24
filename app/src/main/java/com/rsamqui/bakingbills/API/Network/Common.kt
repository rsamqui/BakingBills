package com.rsamqui.bakingbills.API.Network

import com.rsamqui.bakingbills.API.ApiService

object Common {
    private val BASE_URL = "https://baking-bills-new-api.herokuapp.com/"
    val retrofitService: ApiService
    get() = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
}