package com.stepanov.testsip.repository

import com.stepanov.testsip.repository.dto.ResponseApi
import com.stepanov.testsip.repository.dto.ResponseApiItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val apiService: ApiService = Retrofit.Builder().apply {
        baseUrl("https://jsonplaceholder.typicode.com")
        addConverterFactory(GsonConverterFactory.create())
    }.build().create(ApiService::class.java)

    fun getUsers(callbackUsers: CallbackUsers){
        apiService.loadUsers()
            .enqueue(object : Callback<ResponseApi>{
                override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                    val serverResponse: ResponseApi? = response.body()
                    if (response.isSuccessful && serverResponse != null) {
                        val usersList = serverResponse.users
                        callbackUsers.onResponse(usersList)
                    } else {
                        callbackUsers.onFailure(Throwable("Ошибка запроса на сервер"))
                    }
                }


                override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}