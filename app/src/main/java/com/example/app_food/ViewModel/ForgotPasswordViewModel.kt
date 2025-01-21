package com.example.app_food.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.app_food.Model.ChancePassword
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordViewModel : ViewModel() {

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage


    fun sendOTP(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.sendOTP(ChancePassword.SendOTPRequest(email))
                _statusMessage.value = response.message
                Log.d("OTP", response.message)
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun verifyOTP(userId: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.verifyOTP(ChancePassword.VerifyOTPRequest(userId, otp))
                // Kiểm tra phản hồi từ API
                if (response.status == "VERIFIED") {
                    // Mã OTP hợp lệ, có thể tiếp tục đổi mật khẩu
                    _statusMessage.value = "Mã OTP hợp lệ. Bạn có thể tiếp tục đổi mật khẩu."
                    Log.d("OTPpp", response.message)
                } else {
                    // Mã OTP không hợp lệ
                    _statusMessage.value = response.message ?: "Mã OTP không hợp lệ. Vui lòng kiểm tra lại."
                    Log.d("OTP", response.message)
                }

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
