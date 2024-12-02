package com.example.app_food.ScreenAdmin

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_food.Model.User
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun User(userViewModel: UserViewModel) {
    val listuse by userViewModel.user.observeAsState(initial = emptyList())
//    var listuser by remember { mutableStateOf(userViewModel.user.value ?: emptyList()) }
    var mutableList by remember { mutableStateOf(listuse) }
    if (listuse.isEmpty()) {
        userViewModel.fetch()
//        listuse = userViewModel.user.value ?: emptyList() // Cập nhật lại danh sách
//        mutableList = listuse // Cập nhật danh sách từ LiveData
    }else{
        userViewModel.fetch()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Danh sách người dùng",
                fontSize = 30.sp,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listuse) { user ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                userViewModel.deleteUser(user.email)
                                mutableList = mutableList.filter { it.email != user.email } // Loại bỏ khỏi danh sách

                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize() // Đảm bảo nền xóa bao phủ toàn chiều rộng
                                    .background(Color.Red)
                                    .padding(horizontal = 20.dp), // Thêm padding nếu cần
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = "Delete",
                                    color = Color.White
                                )
                            }
                        }
                    ) {
                        itemUser(user) // Nội dung khớp với SwipeToDismissBox
                    }

                }
            }
        }
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .background(color = Color(0xFF673AB7), shape = CircleShape)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

@Composable
fun itemUser(user: User) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)// Sử dụng toàn bộ chiều rộng
            .padding(8.dp) // Điều chỉnh padding để item đẹp hơn
    ) {
        Column(modifier = Modifier.padding(8.dp)) { // Thêm padding bên trong item
            Text(text = user.name)
            Text(text = user.address)
            Text(text = user.sex.toString())
            Text(text = user.email)
        }
    }
}
