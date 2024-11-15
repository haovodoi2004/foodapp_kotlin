package com.example.app_food.Repository

import com.example.app_food.Model.User
import com.example.app_food.Retrofit.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun addser(user:User): Response<User> {
        return RetrofitInstance.api.adduser(user)
    }

    suspend fun login(user: User): Response<User> {
        return RetrofitInstance.api.login(user)
    }
}