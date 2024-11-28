package com.example.app_food.ScreenAdmin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_food.Model.Protype
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.app_food.ViewModel.ProtypeViewModel

@Composable
fun Productype(viewModel: ProtypeViewModel= ProtypeViewModel()){
    val protypeItems by viewModel.protypeItems.observeAsState(initial = emptyList())
    var showdialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if(protypeItems.isEmpty()){
            viewModel.fetchProtype()
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally, ) {
            Text(
                text = "Danh sách loại sản phẩm",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )

            Box() {
                if (protypeItems.isEmpty()) {
                    Text(text = "Đang tải dữ liệu")
                } else {
                    LazyColumn {
                        items(protypeItems) { protype ->
                            ProtypeItem(protype,viewModel)
                        }
                    }
                }
            }
        }
        IconButton(
            onClick = {
                showdialog=true
            },
            modifier = Modifier
                .background(color = Color(0xFF673AB7), shape = CircleShape)
                .size(50.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }

        if(showdialog){
            modalDialog(showdialog,onDissmiss={showdialog=false},protypeViewModel = ProtypeViewModel(),protypeItems = protypeItems)
        }
    }
}

@Composable
fun updateDialog(show : Boolean , onDissmiss: () -> Unit , protye: Protype , protypeViewModel: ProtypeViewModel=ProtypeViewModel()){
   var name by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDissmiss,
        title = { Text(text = "Sửa thông tin") },
        text = {
            OutlinedTextField(value = name , onValueChange = {name = it}, label = { Text(text = "Nhập tên loại")}, modifier = Modifier.fillMaxWidth(0.8f))
        }, dismissButton = {
            Button(onClick = {onDissmiss()}) {
                Text(text = "Hủy")
            }
        }, confirmButton = {
            Button(onClick = {
                val pro = Protype(name)
                protypeViewModel.updateprotype(protye.name, pro)
                onDissmiss()
            }) {
                Text(text = "Ok")
            }
        })
}

@Composable
fun deleteDialog(show: Boolean, onDissmiss: () -> Unit, name : String,protypeViewModel: ProtypeViewModel) {
    AlertDialog(onDismissRequest = onDissmiss,
        title = { Text(text = "Thông báo") },
        text = {
        Text(text = "Bạn có muốn xóa không ?")
        },
        dismissButton = {
            Button(onClick = {onDissmiss()}) {
                Text(text = "Hủy")
            }
        },
        confirmButton = {
            Button(onClick = {protypeViewModel.deleteprotype(name)
            onDissmiss()}) {
                Text(text = "Ok")
            }
        })
}

@Composable
fun modalDialog(show : Boolean,onDissmiss : ()-> Unit,protypeViewModel: ProtypeViewModel,protypeItems: List<Protype>){
    var nameprotype by remember { mutableStateOf("") }
    val context = LocalContext.current
    var number by remember { mutableStateOf(0) }
    for(protype in protypeItems){
        if(protype.name==nameprotype){
            number
        }else{
            number=1

        }
    }
    AlertDialog(onDismissRequest = {onDissmiss},
        title = { Text(text = "Thêm loại sản phẩm") },
        text = {
            OutlinedTextField(value = nameprotype, onValueChange = {nameprotype=it}, label = { Text(text = "Nhập tên loai sản phẩm") })
        }, confirmButton = {
            Button(onClick = {
                if(nameprotype.isEmpty()){
                    Toast.makeText(context,"Bạn ko được bỏ trống",Toast.LENGTH_LONG).show()
                }else{
                    if(number==0){
                        Toast.makeText(context,"Loại sản phầm này đã tồn tại",Toast.LENGTH_LONG).show()
                    }else{
                        onDissmiss()
                        val protype=Protype(nameprotype)
                        protypeViewModel.addprotype(protype)
                        Toast.makeText(context,"Thêm thành công ${nameprotype}",Toast.LENGTH_LONG).show()
                    }
                }

            }) {
                Text(text = "Ok")
            }
        }, dismissButton = {
            Button(onClick = onDissmiss) {
                Text(text = "Hủy")
            }
        })
}

@Composable
fun ProtypeItem(protye : Protype,viewModel: ProtypeViewModel){
    var showdialogdelete by remember { mutableStateOf(false) }
    var showdialogupdate by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .fillMaxWidth(0.8f)
        .padding(top = 10.dp)) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${protye.name}", fontSize = 20.sp, modifier = Modifier.fillMaxWidth(0.6f))
            Row() {

                IconButton(onClick = { showdialogdelete=true }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                }

                IconButton(onClick = {showdialogupdate=true}) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "")
                }
            }
        }
    }
    if(showdialogupdate){
        updateDialog(showdialogupdate, onDissmiss = {showdialogupdate=false},protye,protypeViewModel = viewModel)
    }
    if(showdialogdelete){
        deleteDialog(showdialogdelete, onDissmiss = {showdialogdelete=false},protye.name.toString(), protypeViewModel = viewModel)
    }
}