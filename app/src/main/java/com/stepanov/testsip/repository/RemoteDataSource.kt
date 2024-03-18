package com.stepanov.testsip.repository

import com.stepanov.testsip.repository.dto.ResponseApi
import com.stepanov.testsip.utils.BASE_URL
import com.stepanov.testsip.utils.REQUEST_ERROR
import com.stepanov.testsip.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val apiService: ApiService = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
    }.build().create(ApiService::class.java)

    fun getUsers(callbackUsers: CallbackUsers) {
        apiService.loadUsers()
            .enqueue(object : Callback<ResponseApi> {
                override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                    val serverResponse: ResponseApi? = response.body()
                    if (response.isSuccessful && serverResponse != null) {
                        callbackUsers.onResponse(serverResponse)
                    } else {
                        callbackUsers.onFailure(Throwable(REQUEST_ERROR))
                    }
                }

                override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                    callbackUsers.onFailure(Throwable(SERVER_ERROR))
                }
            })
    }
}