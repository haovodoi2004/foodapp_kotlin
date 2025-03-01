package com.example.app_food.Screen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_food.ViewModel.ForgotPasswordViewModel

@Composable
fun OTP(navController: NavController, forgotPasswordViewModel: ForgotPasswordViewModel, email: String) {
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val otpStatusMessage by forgotPasswordViewModel.statusMessage.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nhập mã OTP",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E4A59)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nhập mã OTP được gửi qua email.",
                fontSize = 16.sp,
                color = Color(0xFF6A6A6A),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Mã OTP") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF3E4A59),
                    unfocusedIndicatorColor = Color(0xFFB0BEC5),
                    cursorColor = Color(0xFF3E4A59)
                )
            )

            if (showError) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            if (otpStatusMessage.isNotEmpty()) {
                Text(
                    text = otpStatusMessage,
                    color = if (otpStatusMessage.contains("thành công")) Color.Green else Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (otp.isNotEmpty()) {
                        forgotPasswordViewModel.verifyOTP(email, otp)  // Kiểm tra OTP
                    } else {
                        showError = true
                        errorMessage = "Vui lòng nhập mã OTP."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E4A59))
            ) {
                Text(
                    text = "Xác Nhận",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

// Chuyển màn nếu OTP đúng
            LaunchedEffect(otpStatusMessage) {
                Log.d("OTPppppp", otpStatusMessage)
                if (otpStatusMessage == "Mã OTP hợp lệ. Bạn có thể tiếp tục đổi mật khẩu.") {  // ✅ So sánh chính xác
                    navController.navigate("resetpassword/$email")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text(
                    text = "Quay lại",
                    color = Color(0xFF3E4A59),
                    fontSize = 14.sp
                )
            }
        }
    }
}