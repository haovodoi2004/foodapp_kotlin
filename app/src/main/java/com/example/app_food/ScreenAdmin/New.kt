package com.example.app_food.ScreenAdmin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "New", fontSize = 30.sp, textAlign = TextAlign.Center)
            if (listnew.isEmpty()) {
                Text(text = "Đang tải dữ liệu", textAlign = TextAlign.Center)
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color.White),  verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(listnew, key = { it.id!! }) { item ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {

                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    item.id?.let { id ->
                                        show = true
                                        selectId = id
                                    }
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
                                        .fillMaxSize()
                                        .background(Color.Red, shape = RoundedCornerShape(12.dp))
                                        .padding(horizontal = 20.dp)
                                        ,
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        ) {
                                NewItem(onClick,item, newViewModel = viewModel, navController = navController)
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
        }, modifier = Modifier.background(color = Color(0xFF673AB7), shape = CircleShape)
            .size(50.dp).align(Alignment.BottomEnd)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

@Composable
fun NewItem(
    onClick: (String) -> Unit,
    new: New,
    newViewModel: NewViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    Card(
        onClick = {


        },
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = new.avatar ?: "default_image_url", // URL mặc định nếu avatar null
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(1f) // Đảm bảo ảnh vuông
                    .clip(RoundedCornerShape(8.dp)), // Bo góc ảnh
                contentScale = ContentScale.Crop // Ảnh hiển thị đẹp hơn
            )

            Spacer(modifier = Modifier.width(12.dp)) // Tạo khoảng cách giữa ảnh và nội dung

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = new.title ?: "Không có tiêu đề",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp)) // Khoảng cách giữa các dòng

                Text(
                    text = new.content ?: "Không có nội dung",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp)) // Tạo khoảng cách với nút bên dưới

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = {
                        onClick(new.id)
                    }) {
                        Text(
                            text = "Xem chi tiết",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
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