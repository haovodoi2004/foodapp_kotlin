package com.example.app_food.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.app_food.Model.ChancePassword
import com.example.app_food.Retrofit.RetrofitInstance

class ForgotPasswordViewModel : ViewModel() {

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun sendOTP(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.sendOTP(ChancePassword.SendOTPRequest(email))
                _statusMessage.value = response.message
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun verifyOTP(userId: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.verifyOTP(ChancePassword.VerifyOTPRequest(userId, otp))
                _statusMessage.value = response.message
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun resetPassword(userId: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.resetPassword(ChancePassword.ResetPasswordRequest(userId, newPassword))
                _statusMessage.value = response.message
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            }
        }
    }
}
