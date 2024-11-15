package com.example.app_food.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.ApiServer.Apiserver
import com.example.app_food.Model.User
import com.example.app_food.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class UserViewModel(private val repository: Repository) : ViewModel(){
    val datauser : MutableLiveData<Response<User>> = MutableLiveData()
    val loginResponse : MutableLiveData<Response<User>> = MutableLiveData()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loginErrorMessage: MutableLiveData<String> = MutableLiveData()

    fun addUser(user:User){
        viewModelScope.launch {
            val response=repository.addser(user)
            datauser.value=response
        }
    }

    // Hàm đăng nhập
    fun login(user: User) {
        viewModelScope.launch {
            val response = repository.login(user)
            if (response.isSuccessful && response.body() != null) {
                loginStatus.value = true  // Đăng nhập thành công

            } else {
                loginStatus.value = false // Đăng nhập thất bại
                loginErrorMessage.value = response.message() // Lưu thông báo lỗi nếu có
            }
        }
    }
    fun resetLoginStatus() {
        loginStatus.value = null
        loginErrorMessage.value = null
    }
}