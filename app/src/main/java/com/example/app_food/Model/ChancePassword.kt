package com.example.app_food.Model

class ChancePassword {
    data class SendOTPRequest(val email: String)
    data class VerifyOTPRequest(val email: String, val otp: String)
    data class ResetPasswordRequest(val userId: String, val newPassword: String)

    data class ApiResponse(val status: String, val message: String)

}