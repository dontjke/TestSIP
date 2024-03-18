package com.stepanov.testsip.repository

import com.stepanov.testsip.repository.dto.ResponseApi
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    fun loadUsers(): Call<ResponseApi>
}