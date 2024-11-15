package com.example.app_food.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_food.Repository.Repository
import retrofit2.Response

class UserViewModelFactory(private val respository:Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){

            return UserViewModel(respository) as T
        }
       throw IllegalAccessException("Unknow ViewModel class")
    }
}