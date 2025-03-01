package com.example.app_food.Toptab

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_food.Model.User
import com.example.app_food.R
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun Mainuser(viewModel: UserViewModel){
    val tabItem = listOf(
        tabb(
            unselected = R.drawable.userr,
            selected = R.drawable.userr
        ),
        tabb(
            unselected = R.drawable.padlock,
            selected = R.drawable.padlock
        ),
        tabb(
            unselected = R.drawable.delete,
            selected = R.drawable.delete
        )
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItem.forEachIndexed { index, item ->
                Tab(selected = index == selectedTabIndex, onClick = {
                    selectedTabIndex = index
                },
                    modifier = Modifier.weight(1f),

                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (index == selectedTabIndex) {
                                    item.selected
                                } else item.unselected
                            ), modifier = Modifier.size(30.dp),
                            contentDescription = ""
                        )
                    })
            }
        }
        when (selectedTabIndex) {
            0 -> User1(viewModel)
            1 -> User2(viewModel)
            2 -> User3(viewModel)

        }
    }
}

@Composable
fun User1(userViewModel: UserViewModel){
    val listuse by userViewModel.user.observeAsState(initial = emptyList())
    var show by remember { mutableStateOf(false) }
    var selectId by remember { mutableStateOf("") }
    var userr = User("","","","","",0,0)
    LaunchedEffect(Unit) {
        userViewModel.fetch() // Chỉ cần gọi fetch một lần
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White)
        ) {
            items(listuse, key = { it.id!! }) { item ->

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {

                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            item.id?.let { id ->
                                show = true
                                selectId = id
                                userr=item
                            }
                            false
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
                                .fillMaxSize()
                                .background(Color.Red,shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(text = "Delete")
                        }
                    }
                ) {
                    if(item.status==0){
                        itemUser(item, userViewModel,listuse)
                    }
                }

                // Reset dismiss state after deletion
                LaunchedEffect(dismissState.currentValue) {
                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        dismissState.snapTo(SwipeToDismissBoxValue.StartToEnd)
                    }
                }
            }

        }
        if (show) {
            userDelete(ondismiss = { show = false },
                onConfirm = {
                    val user = User(selectId,userr.email,userr.password,userr.name,userr.address,userr.sex,2)
                    userViewModel.updateUser(selectId,user)
                    show = false
                })
        }
    }
}

@Composable
fun User2(userViewModel: UserViewModel){
    val listuser by userViewModel.user.observeAsState(initial = emptyList())
    LaunchedEffect(listuser) {
        if(listuser.isEmpty()) {
            userViewModel.fetch()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Danh sách tài khoản bị khóa", fontSize = 25.sp, textAlign = TextAlign.Center)
            LazyColumn() {
                items(listuser, key = { it.id!! }){
                    item->
                    if(item.status==1) {
                        itemUser(item, userViewModel, listuser)
                    }
                }
            }
        }
    }
}

@Composable
fun User3(userViewModel: UserViewModel){
    val userlist by userViewModel.user.observeAsState(initial = emptyList())
    LaunchedEffect(userlist) {
        if (userlist.isEmpty()) {
            userViewModel.fetch()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Danh sách tài khoản bị xóa", textAlign = TextAlign.Center, fontSize = 25.sp)
            LazyColumn {
                items(userlist, key = {it.id!!}){
                    item->
                    if(item.status==2) {
                        itemUser(item, userViewModel, userlist)
                    }
                }
            }
        }
    }
}

@Composable
fun itemUser(user: User, userViewModel: UserViewModel, list: List<User>) {
    var showDetail by remember { mutableStateOf(false) }
    var showUpdate by remember { mutableStateOf(false) }

    Card(

        modifier = Modifier
            .fillMaxWidth()
        .clickable { showDetail = true },
    elevation = CardDefaults.elevatedCardElevation(6.dp),
    shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = user.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (user.sex == 0) "Nam" else "Nữ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (user.status != 2) {
                Button(
                    onClick = {
                        val updatedUser = user.copy(status = if (user.status == 0) 1 else 0)
                        userViewModel.updateUser(user.id.toString(), updatedUser)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (user.status == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = if (user.status == 0) "Khóa" else "Mở khóa")
                }
            }
        }
    }

    if (showDetail) {
        showUserDetail(
            onDismiss = { showDetail = false },
            onUpdate = {
                showDetail = false
                showUpdate = true
            },
            user = user
        )
    }

    if (showUpdate) {
        showUserUpdate(
            user = user,
            onDismiss = { showUpdate = false },
            userViewModel = userViewModel,
            list = list
        )
    }
}

@Composable
fun showUserDetail(onDismiss: () -> Unit, onUpdate: () -> Unit, user: User) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Thông tin chi tiết", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        UserInfoRow(Icons.Default.Person, "Họ và tên", user.name)
                        UserInfoRow(Icons.Default.Email, "Email", user.email)
                        UserInfoRow(Icons.Default.Home, "Địa chỉ", user.address)
                        UserInfoRow(Icons.Default.Lock, "Mật khẩu", "••••••••") // Ẩn mật khẩu
                        UserInfoRow(Icons.Default.Male, "Giới tính", user.sex.toString())
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onDismiss()
                onUpdate()
            }) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sửa")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ok")
            }
        }
    )
}

@Composable
fun UserInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
fun showUserUpdate(user: User, onDismiss: () -> Unit, userViewModel: UserViewModel, list: List<User>) {
    var name by remember { mutableStateOf(user.name) }
    var address by remember { mutableStateOf(user.address) }
    var sex by remember { mutableStateOf(user.sex) }
    var email by remember { mutableStateOf(user.email) }
    var pass by remember { mutableStateOf(user.password) }
    var showDialog by remember { mutableStateOf(false) }
    var showEmailWarning by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Sửa thông tin",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Họ và tên") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Địa chỉ") })
                OutlinedTextField(value = sex.toString(), onValueChange = { sex = it.toInt() }, label = { Text("Giới tính") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(
                    value = pass,
                    onValueChange = {},
                    label = { Text("Mật khẩu") },
                    enabled = false // Vô hiệu hóa chỉnh sửa
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isEmpty() || address.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(context, "Bạn không được bỏ trống dữ liệu", Toast.LENGTH_LONG).show()
                } else {
                    if (name != user.name || email != user.email || pass != user.password) {
                        showDialog = true
                    } else {
                        val updatedUser = User(user.id, email, pass, name, address, sex, 0)
                        userViewModel.updateUser(user.id.toString(), updatedUser)
                        onDismiss()
                    }
                }
            }) {
                Text(text = "Lưu")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text(text = "Hủy")
            }
        }
    )

    if (showEmailWarning) {
        AlertDialog(
            onDismissRequest = { showEmailWarning = false },
            title = { Text(text = "Thông báo") },
            text = { Text(text = "Email này đã được đăng ký vào tài khoản khác") },
            confirmButton = {
                Button(onClick = {
                    showEmailWarning = false
                    onDismiss()
                }) {
                    Text(text = "Ok")
                }
            }
        )
    }

    if (showDialog) {
        val updatedUser = User(user.id, email, pass, name, address, sex, 0)
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Thông báo") },
            text = { Text(text = "Bạn đang thay đổi thông tin bảo mật của người dùng") },
            confirmButton = {
                Button(onClick = {
                    if (list.any { it.email == email }) {
                        showEmailWarning = true
                        showDialog = false
                    } else {
                        userViewModel.updateUser(user.id.toString(), updatedUser)
                        showDialog = false
                        onDismiss()
                    }
                }) {
                    Text(text = "Xác nhận")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text(text = "Hủy")
                }
            }
        )
    }
}


@Composable
fun userDelete(ondismiss: () -> Unit, onConfirm: () -> Unit) {

    AlertDialog(onDismissRequest = { ondismiss() },
        title = { Text(text = "THông báo") },
        text = {
            Text(text = "Bạn có muốn xóa vĩnh viễn tài khoản này không?")
        },
        confirmButton = {
            Button(onClick = {
                onConfirm()
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = { ondismiss() }) {
                Text(text = "Hủy")
            }
        })
}

data class tabb(
    val unselected: Int,
    val selected: Int
)