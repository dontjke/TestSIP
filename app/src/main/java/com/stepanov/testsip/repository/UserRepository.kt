package com.stepanov.testsip.repository

interface UserRepository {
    fun getUsersFromServer(callbackUsers: CallbackUsers)
    fun getUserDetails()
}