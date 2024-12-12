//package com.example.app_food.ScreenAdmin
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.detectHorizontalDragGestures
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SwipeToDismissBox
//import androidx.compose.material3.SwipeToDismissBoxState
//import androidx.compose.material3.SwipeToDismissBoxValue
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.rememberSwipeToDismissBoxState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.app_food.Model.User
//import com.example.app_food.ViewModel.UserViewModel
//import androidx.compose.material3.AlertDialog as AlertDialog1
//
//@Composable
//fun User(userViewModel: UserViewModel) {
//    val listuse by userViewModel.user.observeAsState(initial = emptyList())
//    var show by remember { mutableStateOf(false) }
//    var selectId by remember { mutableStateOf("") }
//    LaunchedEffect(Unit) {
//        userViewModel.fetch() // Chỉ cần gọi fetch một lần
//    }
//
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(innerpadding)
//                .background(Color.White)
//        ) {
//            items(listuse, key = { it.id!! }) { item ->
//                val dismissState = rememberSwipeToDismissBoxState(
//                    confirmValueChange = {
//
//                        if (it == SwipeToDismissBoxValue.EndToStart) {
//                            item.id?.let { id ->
//                                show = true
//                                selectId = id
//                            }
//                            false
//                        } else {
//                            false
//                        }
//                    }
//                )
//
//                SwipeToDismissBox(
//                    state = dismissState,
//                    backgroundContent = {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Color.Red)
//                                .padding(horizontal = 20.dp),
//                            contentAlignment = Alignment.CenterEnd
//                        ) {
//                            Text(text = "Delete")
//                        }
//                    }
//                ) {
//                    if(item.status==0){
//                        itemUser(item, userViewModel,listuse)
//                    }
//                }
//
//                // Reset dismiss state after deletion
//                LaunchedEffect(dismissState.currentValue) {
//                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
//                        dismissState.snapTo(SwipeToDismissBoxValue.StartToEnd)
//                    }
//                }
//            }
//        }
//        if (show) {
//            userDelete(ondismiss = { show = false },
//                onConfirm = {
//                    userViewModel.deleteUser(selectId)
//                    show = false
//                })
//        }
//    }
//}
//
////@Composable
////fun itemUser(user: User, userViewModel: UserViewModel,list : List<User>) {
////    var showDetail by remember { mutableStateOf(false) }
////    var showUpdate by remember { mutableStateOf(false) } // Quản lý trạng thái sửa
////
////    Card(
////        onClick = {
////            showDetail = true
////        },
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(8.dp)
////    ) {
////        Column(modifier = Modifier.padding(8.dp)) {
////            Text(text = user.name)
////            Text(text = user.address)
////            Text(text = user.sex.toString())
////            Text(text = user.email)
////            Button(onClick = {
////                val userr = User(user.id,user.email,user.password,user.name,user.address,user.sex,1)
////                userViewModel.updateUser(user.id.toString(),userr)
////            }) {
////                Text(text = "KHóa tài khoản")
////            }
////        }
////    }
////
////    if (showDetail) {
////        showUserDetail(
////            onDismiss = { showDetail = false },
////            onUpdate = {
////
////                showDetail = false
////                showUpdate = true
////            },
////            user = user
////        )
////    }
////
////
////
////    if (showUpdate) {
////        showUserUpdate(
////            user = user,
////            onDismiss = { showUpdate = false },
////            userViewModel = userViewModel,
////            list
////        )
////    }
////}
////
////@Composable
////fun showUserDetail(onDismiss: () -> Unit, onUpdate: () -> Unit, user: User) {
////    AlertDialog(
////        onDismissRequest = { onDismiss() },
////        title = { Text(text = "Thông tin chi tiết") },
////        text = {
////            Column {
////                Text(text = "${user.name}")
////                Text(text = "${user.email}")
////                Text(text = "${user.address}")
////                Text(text = "${user.password}")
////                Text(text = "${user.sex}")
////            }
////        },
////        confirmButton = {
////            Button(onClick = {
////                onDismiss()
////                onUpdate() // Gọi hàm chuyển trạng thái hiển thị sang sửa
////            }) {
////                Text(text = "Sửa")
////            }
////        },
////        dismissButton = {
////            Button(onClick = { onDismiss() }) {
////                Text(text = "Ok")
////            }
////        }
////    )
////}
////
////
////@Composable
////fun showUserUpdate(user: User, onDismiss: () -> Unit,userViewModel: UserViewModel,list : List<User>) {
////    var name by remember { mutableStateOf(user.name) }
////    var adress by remember { mutableStateOf(user.address) }
////    var sex by remember { mutableStateOf(user.sex) }
////    var email by remember { mutableStateOf(user.email) }
////    var pass by remember { mutableStateOf(user.password) }
////    var show by remember { mutableStateOf(false) }
////    var show1 by remember { mutableStateOf(false) }
////    val context = LocalContext.current
////    AlertDialog(onDismissRequest = { onDismiss() },
////        title = { Text(text = "Sửa thông tin", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
////        text = {
////            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
////                OutlinedTextField(value = name, onValueChange = { name = it }, label = {})
////                OutlinedTextField(value = adress, onValueChange = { adress = it }, label = {})
////                OutlinedTextField(
////                    value = sex.toString(),
////                    onValueChange = { sex = it.toInt() },
////                    label = {})
////                OutlinedTextField(value = email, onValueChange = { email = it }, label = {})
////                OutlinedTextField(value = pass, onValueChange = { pass = it }, label = {})
////            }
////        },
////        confirmButton = {
////            Button(onClick = {
////                if(name.isEmpty()||adress.isEmpty()||email.isEmpty()||pass.isEmpty()){
////                    Toast.makeText(context,"Bạn không được bỏ trống dữ liệu",Toast.LENGTH_LONG).show()
////                }else{
////                    if(name!=user.name||email!=user.email||pass!=user.password){
////                        show=true
////                    }else{
////                                val user = User(user.id,email,pass,name,adress,sex,0)
////                                userViewModel.updateUser(user.id.toString(),user)
////                                onDismiss()
////                    }
////                }
////            }) {
////                Text(text = "Ok")
////            }
////        },
////        dismissButton = {
////            Button(onClick = {onDismiss()}) {
////                Text(text = "Hủy")
////            }
////        })
////
////    if(show1){
////        AlertDialog(
////            onDismissRequest = { show1 = false },
////            title = { Text(text = "Thông báo") },
////            text = { Text(text = "Email này đã được đăng ký vào tài khoản khác") },
////            confirmButton = {
////                Button(onClick = {
////                    show1=false
////                    onDismiss()
////                }) {
////                    Text(text = "Ok")
////                }
////            })
////    }
////
////    if(show){
////        val user = User(user.id,email,pass,name,adress,sex,0)
////        AlertDialog(
////            onDismissRequest = { show = false },
////            title = { Text(text = "Thông báo") },
////            text = { Text(text = "Bạn đang thay đổi thông tin bảo mật của người dùng") },
////            confirmButton = {
////                Button(onClick = {
////                    for(item in list){
////                       if(item.email==email.toString()){
////                           show1=true
////                           show=false
////                           break
////                       }else{
////                           userViewModel.updateUser(user.id.toString(),user)
////                           show=false
////                           onDismiss()
////                       }
////                    }
////                }) {
////                    Text(text = "Ok")
////                }
////            },
////            dismissButton = {
////                Button(onClick = {show=false}) {
////                    Text(text = "Hủy")
////                }
////            })
////    }
////}
////
////
////@Composable
////fun userDelete(ondismiss: () -> Unit, onConfirm: () -> Unit) {
////
////    AlertDialog1(onDismissRequest = { ondismiss() },
////        title = { Text(text = "THông báo") },
////        text = {
////            Text(text = "Bạn có muốn xóa vĩnh viễn tài khoản này không?")
////        },
////        confirmButton = {
////            Button(onClick = {
////                onConfirm()
////            }) {
////                Text(text = "Ok")
////            }
////        },
////        dismissButton = {
////            Button(onClick = { ondismiss() }) {
////                Text(text = "Hủy")
////            }
////        })
////}
