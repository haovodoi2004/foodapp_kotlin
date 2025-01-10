package com.example.app_food.Bottombar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_food.Model.User
import com.example.app_food.R
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun userDetail(email : String,navController: NavController,userViewModel: UserViewModel){
    val user by userViewModel.userr.observeAsState()
    var show by remember { mutableStateOf(false) }
    var uerr = User("","","","","",0,0)
    LaunchedEffect(email) {
        userViewModel.getuser(email)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 20.dp).fillMaxWidth()) {
            user?.let {
                us->
                uerr=User(us.id,us.email,us.password,us.name,us.address,us.sex,us.status)
                IconButton(onClick = {navController.navigateUp()}, modifier = Modifier.align(alignment = Alignment.Start)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
                Text(text = "Thông tin nguoi dung", fontSize = 30.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Image(painter = painterResource(R.drawable.user), contentDescription = "", modifier = Modifier.size(70.dp))
                Text(text = "Họ và tên : ${us.name}", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                Text(text = "Email : ${us.email}", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                Text(text = "Địa chỉ : ${us.address}", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                if(us.sex==0){
                    Text(text = "Giới tính : nam", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                }else{
                    Text(text = "Giới tính : nữ", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                }
                Text(text = "status ${us.status}")
            }
        }
        Button(onClick = {show=true}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)), modifier = Modifier.align(
            Alignment.BottomCenter)){
            Text(text = "Sửa thông tin")
        }
        if(show){
         updateDialog(onDismiss = {show=false},uerr,userViewModel)
        }
    }
}

@Composable
fun updateDialog(onDismiss : ()-> Unit,user: User,userViewModel: UserViewModel) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var address by remember { mutableStateOf(user.address) }
    var sex by remember { mutableStateOf(user.sex) }
    AlertDialog(
        onDismissRequest = {onDismiss()},
        title = { Text(text = "Sửa thông tin") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Nhập họ và tên") })
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Nhập email") })
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = "Nhập địa chỉ") })
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = sex == 0,
                            onClick = { sex = 0 },
                            modifier = Modifier.fillMaxWidth(0.1f)
                        )
                        Text(text = "Nam")
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = sex == 1,
                            onClick = { sex = 1 },
                            modifier = Modifier.fillMaxWidth(0.1f)
                        )
                        Text(text = "Nữ")
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = {
            val user = User(user.id,email,user.password,name,address,sex,user.status)
            userViewModel.updateUser(user.id.toString(),user)
            onDismiss()
        }){
            Text(text = "Ok")
        } },
        dismissButton = {
            TextButton(onClick = {onDismiss()}){
                Text(text = "Hủy")
            }  })
}