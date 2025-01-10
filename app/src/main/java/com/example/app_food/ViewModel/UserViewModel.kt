package com.example.app_food.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.ApiServer.Apiserver
import com.example.app_food.Model.User
import com.example.app_food.Retrofit.RetrofitInstance

import kotlinx.coroutines.launch
import retrofit2.Response


class UserViewModel : ViewModel(){
    val datauser : MutableLiveData<Response<User>> = MutableLiveData()
    val loginResponse : MutableLiveData<Response<User>> = MutableLiveData()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loginErrorMessage: MutableLiveData<String> = MutableLiveData()
    val user = MutableLiveData<List<User>>()
    val userr=MutableLiveData<User?>()

    fun getuser(email: String){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.getuserbyemail(email)
            try {
                if(respone.isSuccessful){
                    userr.postValue(respone.body())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    // Hàm lấy thông tin người dùng từ API dựa vào email
    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getuserbyemail(email)
                if (response.isSuccessful && response.body() != null) {
                    onResult(response.body()) // Trả về User nếu tìm thấy
                } else {
                    onResult(null) // Không tìm thấy
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null) // Xử lý lỗi
            }
        }
    }

    fun updateUser(id : String , user: User) {
        viewModelScope.launch {
            val respone = RetrofitInstance.api.updateuser(id,user)
            try {
                if (respone.isSuccessful) {
                    fetch()
                    val updatedUser = RetrofitInstance.api.getuserbyemail(id)
                    userr.postValue(updatedUser.body())
                } else {
                    Log.e("API update user ", " Error ${respone.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteUser(id : String) {
        viewModelScope.launch {
            val respone = RetrofitInstance.api.deleteuser(id)
            try {
                if (respone.isSuccessful) {
                    fetch()
                } else {
                    Log.e("API delete user ", " Error ${respone.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetch (){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.getlistuser()
            if(respone.isEmpty()){
                user.postValue(emptyList())
            }else{
                user.postValue(respone)
            }
        }
    }

    fun addUser(user:User){
        viewModelScope.launch {
            val response=RetrofitInstance.api.adduser(user)
            datauser.value=response
        }
    }

    // Hàm đăng nhập
    fun login(user: User) {
        viewModelScope.launch {
            Log.d("API Response", "Response: ${user}")
            val response = RetrofitInstance.api.login(user)
            if (response.isSuccessful && response.body() != null) {
                Log.d("API Response", "Response: ${response.body()}")
                val loggedInUser = user
                userr.value = loggedInUser
                loginStatus.value = true
            }
            else {
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