package com.example.app_food.Screen

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
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun SendOTPScreen(navController: NavController,userViewModel: UserViewModel,forgotPasswordViewModel: ForgotPasswordViewModel) {
    var email by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Light background color
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
                text = "Quên Mật Khẩu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E4A59)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nhập địa chỉ email của bạn để nhận liên kết đặt lại mật khẩu.",
                fontSize = 16.sp,
                color = Color(0xFF6A6A6A),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showError = false
                },
                label = { Text("Địa chỉ email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors( // Thay thế outlinedTextFieldColors
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF3E4A59),
                    unfocusedIndicatorColor = Color(0xFFB0BEC5),
                    cursorColor = Color(0xFF3E4A59)
                )
            )

            if (showError) {
                Text(
                    text = "Vui lòng nhập một địa chỉ email hợp lệ.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        forgotPasswordViewModel.sendOTP(email)
                       navController.navigate("otp/$email")
                    } else {

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E4A59))
            ) {
                Text(
                    text = "Gửi Liên Kết Đặt Lại",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {}) {
                Text(
                    text = "Quay lại",
                    color = Color(0xFF3E4A59),
                    fontSize = 14.sp
                )
            }
        }
    }
}
