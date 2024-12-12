package com.example.app_food.ScreenAdmin

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.New
import com.example.app_food.ViewModel.NewViewModel

@Composable
fun Newdetail(newid : String,navController: NavController,viewModel: NewViewModel){
    val newitem by viewModel.neww.observeAsState()
    var show by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(newid) {
        viewModel.getnewbyid(newid)
    }

    if (newitem == null) {
        Text(text = "Đang tải dữ liệu...")
    } else {
        newitem?.let { item ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = item.title ?: "Không có tiêu đề", fontSize = 25.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    AsyncImage(
                        model = item.avatar ?: "default_image_url",
                        contentDescription = "Ảnh",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = item.content ?: "Không có nội dung", modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(), textAlign = TextAlign.Justify)
                    Button(
                        onClick = {show=true},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE14515),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Sửa thông tin")
                        Toast.makeText(context,"${newitem!!.id}",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        if(show){
            updateNew(onDismiss = {show=false},newitem!!,viewModel,newid)
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