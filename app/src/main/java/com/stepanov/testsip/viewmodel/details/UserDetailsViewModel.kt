package com.stepanov.testsip.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stepanov.testsip.repository.CallbackUsers
import com.stepanov.testsip.repository.DefaultUserRepository
import com.stepanov.testsip.repository.RemoteDataSource
import com.stepanov.testsip.repository.dto.ResponseApiItem
import com.stepanov.testsip.viewmodel.users.UsersState

class UserDetailsViewModel(
    private val liveData: MutableLiveData<UsersState> = MutableLiveData(),
    private val defaultUserRepository: DefaultUserRepository = DefaultUserRepository(
        RemoteDataSource()
    )
) : ViewModel(), CallbackUsers {

    fun getLiveData() = liveData


    fun getUser() {
        liveData.postValue(UsersState.Loading)
        defaultUserRepository.getUsersFromServer(this)
    }

    override fun onResponse(usersList: List<ResponseApiItem>) {
        liveData.postValue(UsersState.Success(usersList))
    }

    override fun onFailure(t: Throwable) {
        liveData.postValue(UsersState.Error(t))
    }
}