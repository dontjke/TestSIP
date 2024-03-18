package com.stepanov.testsip.viewmodel.users

import com.stepanov.testsip.repository.dto.ResponseApiItem

sealed class UsersState {
    data object Loading : UsersState()
    data class Success(val usersList: List<ResponseApiItem>) : UsersState()
    data class Error(val error: Throwable) : UsersState()
}