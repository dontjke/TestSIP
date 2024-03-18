package com.stepanov.testsip.repository

class DefaultUserRepository(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {
    override fun getUsersFromServer(callbackUsers: CallbackUsers) {
       remoteDataSource.getUsers(callbackUsers)
    }

    override fun getUserDetails() {
        TODO("Not yet implemented")
    }

}