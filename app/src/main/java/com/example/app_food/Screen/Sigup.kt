package com.example.app_food.Screen

import android.provider.MediaStore.Audio.Radio
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_food.Model.User
import com.example.app_food.R
import com.example.app_food.Repository.Repository
import com.example.app_food.ViewModel.UserViewModel
import com.example.app_food.ViewModel.UserViewModelFactory


@Composable
fun Sigup(navController: NavController, userViewModel: UserViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.fillMaxHeight(0.03f))
            IconButton(onClick = { navController.navigateUp() }) {
                Image(
                    contentDescription = "",
                    painter = painterResource(R.drawable.back),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                )
            }

            var email by remember { mutableStateOf("") }
            var sex by remember { mutableStateOf(2) }
            var name by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var rememberMe by remember { mutableStateOf(false) }

            var emailError by remember { mutableStateOf("") }
            var nameError by remember { mutableStateOf("") }
            var addressError by remember { mutableStateOf("") }
            var passwordError by remember { mutableStateOf("") }
            var sexError by remember { mutableStateOf("") }

            var isFormSubmitted by remember { mutableStateOf(false) }


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "SigUp", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "nhập email")
                        emailError=if (isFormSubmitted&&!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email ko hợp lên" else ""},
                    modifier = Modifier.fillMaxWidth(0.9f),
                    isError = emailError.isNotEmpty()
                )
                if (isFormSubmitted&&emailError.isNotEmpty()) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.01f))
                    Text(text = emailError, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "nhập họ và tên")
                        nameError=if (isFormSubmitted&&name.isBlank()) "tên không được để trống" else ""},
                    modifier = Modifier.fillMaxWidth(0.9f),
                    isError = nameError.isNotEmpty()
                )
                if (isFormSubmitted&&nameError.isNotEmpty()) {
                    Text(text = nameError, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
                }
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = "nhập địa chỉ")
                            addressError=if(isFormSubmitted&&address.isBlank()) "bạn ko được để trống địa chỉ" else ""},
                    modifier = Modifier.fillMaxWidth(0.9f),
                    isError = addressError.isNotEmpty()
                )
                if(isFormSubmitted&&addressError.isNotEmpty()){
                    Text(text = addressError, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "nhập mật khẩu")
                            passwordError=if(isFormSubmitted&&password.isBlank()) "bạn không được để trống mật khẩu " else ""},
                    modifier = Modifier.fillMaxWidth(0.9f),
                    isError = passwordError.isNotEmpty()
                )
                if (isFormSubmitted&&passwordError.isNotEmpty()) {
                    Text(text = addressError, color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
                }
                Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Chọn giới tính")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = sex == 0,
                                        onClick = { sex = 0 },
                                        modifier = Modifier.fillMaxWidth(0.1f)
                                    )
                                    Text("Nam")
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    RadioButton(selected = sex == 1, onClick = { sex = 1 })
                                    Text("Nữ")
                                }
                            }
                        }
                    }
                    if (isFormSubmitted&&sex == 2) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                        Text(text = "Giới tính không được để trống", color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
                    }
                }
                Button(onClick = {
                    isFormSubmitted=true
                    if (email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                        name.isNotBlank() && address.isNotBlank() && password.isNotBlank() && sex != 2 ) {

                        userViewModel.addUser(
                            User(
                                email = email,
                                password = password,
                                name = name,
                                address = address,
                                sex = sex
                            )
                        )
                    } else {
                        if (sex == 2) sexError = "Vui lòng chọn giới tính"
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))) {
                    Text(text = "Đăng ký")
                }
            }
        }
    }
}