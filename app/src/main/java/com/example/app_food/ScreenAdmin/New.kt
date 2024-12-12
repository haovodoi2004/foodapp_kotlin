package com.example.app_food.ScreenAdmin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.New
import com.example.app_food.ViewModel.NewViewModel

@Composable
fun New(onClick: (String) -> Unit, viewModel: NewViewModel, navController: NavController){
    var show by remember { mutableStateOf(false) }
    var showadd by remember { mutableStateOf(false) }
    var selectId by remember { mutableStateOf("") }
    val listnew by viewModel.NewItems.observeAsState(initial = emptyList())
    LaunchedEffect(listnew) {
        if(listnew.isEmpty()){
            viewModel.fetchNew()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "New", fontSize = 30.sp, textAlign = TextAlign.Center)
            if (listnew.isEmpty()) {
                Text(text = "Đang tải dữ liệu", textAlign = TextAlign.Center)
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(listnew, key = { it.id!! }) { item ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {

                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    item.id?.let { id ->
                                        show = true
                                        selectId = id
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
                                        .background(Color.Red)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        ) {
                                Newitem(onClick,item, newViewModel = viewModel, navController = navController)
                        }

                    }
                }
            }
        }
        if (show) {
            newDelete(ondismiss = { show = false },
                onConfirm = {
                    viewModel.newdelete(selectId)
                    show = false
                })
        }

        if(showadd){
            newadd(onDismiss = {showadd=false}, viewModel = viewModel)
        }
        IconButton(onClick = {
            showadd=true
        }, modifier = Modifier.align(Alignment.BottomEnd)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

@Composable
fun Newitem(onClick: (String) -> Unit,new : New,newViewModel: NewViewModel,navController: NavController){
    val context = LocalContext.current
    Card(onClick = {
        onClick(new.id)
        Toast.makeText(context,"${new.id.toString()}",Toast.LENGTH_SHORT).show()
    }, modifier = Modifier.padding(top = 10.dp)) {
        Row() {
            AsyncImage(
                model = new.avatar ?: "default_image_url",  // Cung cấp URL mặc định nếu avatar là null
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(1f),
                contentScale = ContentScale.FillBounds// Đảm bảo ảnh có kích thước hợp lý
            )
            Column() {
                Text(text = new.title ?: "Không có tiêu đề")
                Text(text = new.content ?: "Không có nội dung", maxLines = 3)

            }
        }
    }
}

@Composable
fun newadd(onDismiss : () -> Unit,viewModel: NewViewModel) {
    var img by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = {},
        title = { Text(text = "Thêm bản tin", textAlign = TextAlign.Center)},
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = img,
                    onValueChange = { img = it },
                    label = { Text(text = "Nhập link ảnh") })
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Nhập tiêu đề") })
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = "Nhập nội dung") })
            }
        },
        dismissButton = {
            Button(onClick = {onDismiss()}) {
                Text(text = "Hủy")
            }
        },
        confirmButton = {
            Button(onClick = {
                val new = New("",title,content,img)
                viewModel.addnew(new)
                onDismiss()
            }) {
                Text(text = "Ok")
            }
        })
}

@Composable
fun newDelete(ondismiss: () -> Unit, onConfirm: () -> Unit) {

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