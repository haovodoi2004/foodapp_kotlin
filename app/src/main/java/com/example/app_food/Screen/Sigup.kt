package com.example.app_food.Screen

import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_food.Model.User
import com.example.app_food.R
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun SignupScreen(navController: NavController, userViewModel: UserViewModel ) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf(2) }
    var isFormSubmitted by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val emailuser by userViewModel.user.observeAsState(initial = emptyList())
    var number by remember { mutableStateOf(0) }
    var show by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var sexError by remember { mutableStateOf("") }
    val context= LocalContext.current

    LaunchedEffect(emailuser) {
        if(emailuser.isEmpty()){
            userViewModel.fetchuser()
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.95f),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),

                ) {
                    Text(text = "Đăng ký", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (isFormSubmitted) {
                                emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email không hợp lệ" else ""
                            }
                        },
                        label = { Text("Nhập email") },
                        isError = emailError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError.isNotEmpty()) ErrorText(emailError)

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            if (isFormSubmitted) {
                                nameError = if (name.isBlank()) "Tên không được để trống" else ""
                            }
                        },
                        label = { Text("Nhập họ và tên") },
                        isError = nameError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (nameError.isNotEmpty()) ErrorText(nameError)

                    OutlinedTextField(
                        value = address,
                        onValueChange = {
                            address = it
                            if (isFormSubmitted) {
                                addressError = if (address.isBlank()) "Địa chỉ không được để trống" else ""
                            }
                        },
                        label = { Text("Nhập địa chỉ") },
                        isError = addressError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (addressError.isNotEmpty()) ErrorText(addressError)

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if (isFormSubmitted) {
                                passwordError = if (password.isBlank()) "Mật khẩu không được để trống" else ""
                            }
                        },
                        label = { Text("Nhập mật khẩu") },
                        isError = passwordError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu")
                            }
                        }
                    )
                    if (passwordError.isNotEmpty()) ErrorText(passwordError)

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Chọn giới tính")
                    Row(horizontalArrangement = Arrangement.Start) {
                        GenderRadioButton("Nam", sex == 0) { sex = 0 }
                        Spacer(modifier = Modifier.width(16.dp))
                        GenderRadioButton("Nữ", sex == 1) { sex = 1 }
                    }
                    if (sex == 2 && isFormSubmitted) ErrorText("Giới tính không được để trống")

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            isFormSubmitted = true

                            emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email không hợp lệ" else ""
                            nameError = if (name.isBlank()) "Tên không được để trống" else ""
                            addressError = if (address.isBlank()) "Địa chỉ không được để trống" else ""
                            passwordError = if (password.isBlank()) "Mật khẩu không được để trống" else ""
                            sexError = if (sex == 2) "Vui lòng chọn giới tính" else ""

                            if (emailError.isEmpty() && nameError.isEmpty() && addressError.isEmpty() && passwordError.isEmpty() && sexError.isEmpty()) {

                                if (emailuser.any { it.email == email }) {
                                    show=true
                                    Log.d("huidhiuhugigr","true")
                                }else {
                                    Log.d("huidhiuhugigr","false")
                                    Log.d("huidhiuhugigr",emailuser!!.toString()+ "-" +email)
                                    userViewModel.addUser(
                                        User(
                                            email = email,
                                            password = password,
                                            name = name,
                                            address = address,
                                            sex = sex,
                                            status = 0
                                        )
                                    )

                                    navController.navigate("signin")
                                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Đăng ký")
                    }
                    Button(onClick = {navController.navigateUp()},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1679BB)),
                        modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Quay lại")
                    }
                    if(show) {
                        ShowAlterDialog(onDismiss = {
                            show=false
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ShowAlterDialog(onDismiss: () -> Unit = {}){
    AlertDialog(onDismissRequest = {onDismiss()},
        title = {Text(text = "Thông báo")},
        text = {Text(text = "Email đã được đăng ký bởi một tài khoản khác")},
        dismissButton = {
            Button(onDismiss) {
                Text(text = "Ok")
            }
        },
        confirmButton = {})
}

@Composable
fun ErrorText(error: String) {
    Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth())
}

@Composable
fun GenderRadioButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label)
    }
}


