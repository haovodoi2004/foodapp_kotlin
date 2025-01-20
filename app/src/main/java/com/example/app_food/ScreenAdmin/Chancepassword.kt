package com.example.app_food.ScreenAdmin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import org.mindrot.jbcrypt.BCrypt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_food.Model.User
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun ChangePassword(navController: NavController,userViewModel: UserViewModel,email: String) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val user by userViewModel.userr.observeAsState()
    var show by remember { mutableStateOf(false) }
    var show1 by remember { mutableStateOf(false) }
    LaunchedEffect(email) {
        userViewModel.getuser(email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Đổi mật khẩu",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Mật khẩu cũ") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Old Password"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Mật khẩu mới") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LockOpen,
                    contentDescription = "New Password"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Xác nhận mật khẩu mới") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Confirm Password"
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if(oldPassword.isEmpty()||newPassword.isEmpty()||confirmPassword.isEmpty()){
                    show1=true
                }else {
                    user?.let { us ->
                        if (!BCrypt.checkpw(oldPassword, us.password)) {
                            show = true
                            Log.d("fyujfyjfyjh","$oldPassword - ${us.password}")
                        } else {
                            val hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt())
                            val userr = User(
                                email = email,
                                password = hashedNewPassword,
                                name = us.name,
                                address = us.address,
                                sex = us.sex,
                                status = us.status
                            )
                            userViewModel.updateUser(us.id!!, userr)
                            navController.popBackStack()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Xác nhận",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(
                text = "Hủy bỏ",
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        if(show==true){
            show(onDissmiss = {show=false})
        }
        if(show1==true){
            show1(onDissmiss = {show1=false})
        }
    }
}

@Composable
fun show1(onDissmiss :()->Unit){
    AlertDialog(onDismissRequest = {onDissmiss()},

        title = {
            Text("Thông báo")
        },
        text = {
            Text("Bạn không được để trống ô nhập")
        },
        confirmButton = {
            Button(onDissmiss)  {
                Text("Ok")
            }},
    )
}

@Composable
fun show(onDissmiss :()->Unit){
    AlertDialog(onDismissRequest = {onDissmiss()},

        title = {
            Text("Thông báo")
        },
        text = {
            Text("Mật khẩu cũ không đúng")
        },
        confirmButton = {
            Button(onDissmiss)  {
                Text("Ok")
            }},
       )
}