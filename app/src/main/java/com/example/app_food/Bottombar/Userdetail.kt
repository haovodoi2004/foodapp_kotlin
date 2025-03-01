package com.example.app_food.Bottombar

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun userDetail(onClick:()->Unit,email: String, navController: NavController, userViewModel: UserViewModel) {
    val user by userViewModel.userr.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        userViewModel.getuser(email)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Nút Back và Avatar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {   navController.popBackStack() }) { // Nút Back
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.weight(1f)) // Đẩy avatar về giữa
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
                user?.let { us ->
                    Text(
                        text = us.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = us.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // Body: Thông tin chi tiết
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    user?.let { us ->
                        InfoRow(label = "Địa chỉ", value = us.address)
                        InfoRow(label = "Giới tính", value = if (us.sex.toInt() == 0) "Nam" else "Nữ")
                        InfoRow(label = "Trạng thái", value = if (us.status.toInt() == 1) "Hoạt động" else "Không hoạt động")
                    }
                }
            }

            // Footer: Nút sửa thông tin
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Sửa thông tin")
            }
        }

        // Dialog sửa thông tin
        if (showDialog) {
            user?.let { updateDialog(onDismiss = { showDialog = false }, it, userViewModel) }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value ?: "Không có dữ liệu",  // Kiểm tra null và thay thế bằng thông báo "Không có dữ liệu"
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun updateDialog(onDismiss: () -> Unit, user: User, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var address by remember { mutableStateOf(user.address) }
    var sex by remember { mutableStateOf(user.sex) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Sửa thông tin", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nhập họ và tên") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Nhập email") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Nhập địa chỉ") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                Text(text = "Chọn giới tính")
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = sex == 0, onClick = { sex = 0 })
                        Text(text = "Nam", style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = sex == 1, onClick = { sex = 1 })
                        Text(text = "Nữ", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedUser = User(user.id.toString(), email, user.password, name, address, sex.toInt(), user.status.toInt())
                userViewModel.updateUser(user.id.toString(), updatedUser)
                Log.d("jiodjefioehf","${updatedUser}")
                onDismiss()
            }) {
                Text(text = "Ok", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Hủy", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}
