package com.example.app_food.ScreenAdmin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.New
import com.example.app_food.ViewModel.NewViewModel

@Composable
fun Newdetail(newid: String, navController: NavController, viewModel: NewViewModel) {
    val newitem by viewModel.neww.observeAsState()
    var show by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(newid) {
        viewModel.getnewbyid(newid)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Nút quay lại


        if (newitem == null) {
            // Hiển thị trạng thái đang tải dữ liệu
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Đang tải dữ liệu...", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            newitem?.let { item ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { navController.popBackStack() },
                                        modifier = Modifier
                                            .padding(top = 10.dp)

                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Quay lại",
                                            tint = Color.Black,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                    Text(
                                        text = item.title ?: "Không có tiêu đề",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                // Tiêu đề

                                Spacer(modifier = Modifier.height(16.dp))

                                // Ảnh
                                AsyncImage(
                                    model = item.avatar ?: "default_image_url",
                                    contentDescription = "Ảnh",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // Nội dung
                                Text(
                                    text = item.content ?: "Không có nội dung",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Justify
                                )

                                IconButton(
                                    onClick = {
                                        show = true
                                    },
                                    modifier = Modifier.background(
                                        color = Color(0xFF673AB7),
                                        shape = CircleShape
                                    )
                                        .size(50.dp).align(Alignment.BottomEnd)
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                                }
                            }
                        }
                    }
                }
            }
            if (show) {
                updateNew(onDismiss = { show = false }, newitem!!, viewModel, newid)
            }
        }
    }
}

@Composable
fun updateNew(onDismiss : ()->Unit,new : New,viewModel: NewViewModel,id : String ) {
    var title by remember { mutableStateOf(new.title) }
    var img by remember { mutableStateOf(new.avatar) }
    var content by remember { mutableStateOf(new.content) }
    AlertDialog(onDismissRequest = {onDismiss()},
        title = { Text(text = "Sửa thông tin", textAlign = TextAlign.Center) },
        text = {
            Box(modifier = Modifier.fillMaxWidth()){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(value =title , onValueChange = {title=it}, label = { Text(text = "Nhập title") })
                    OutlinedTextField(value =img , onValueChange = {img=it}, label = { Text(text = "Nhập link ảnh") })
                    OutlinedTextField(value =content , onValueChange = {content=it}, label = { Text(text = "Nhập content") })
                }
            }
        },
        confirmButton = {
            Button(onClick = {var new = New(id,title,content,img)
                viewModel.updatenew(id,new)
                onDismiss()}) {
                Text(text = "Ok")
            }

        },
        dismissButton = {
            Button(onClick = {onDismiss()})  {
                Text(text = "Hủy")
            }
        })
}