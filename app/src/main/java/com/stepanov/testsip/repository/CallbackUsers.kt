package com.stepanov.testsip.repository

import com.stepanov.testsip.repository.dto.ResponseApiItem

interface CallbackUsers {
    fun onResponse(usersList: List<ResponseApiItem>)
    fun onFailure(t: Throwable)
}