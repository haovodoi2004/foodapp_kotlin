package com.example.app_food.Screen

<<<<<<< HEAD
=======
import android.app.AlertDialog
import android.widget.Toast
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
<<<<<<< HEAD
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
=======
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetail(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)){

        }

        Box(modifier = Modifier.weight(7f).fillMaxSize().background(Color.Black)){
            Box(modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)).background(Color.White).fillMaxSize()){

            }
        }
    }
}
=======
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.R
import com.example.app_food.ViewModel.ProViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun ProductDetail(
    navController: NavController,
    produc: String,
    viewModel: ProViewModel = ProViewModel()
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    val show = remember { mutableStateOf(false) }
    LaunchedEffect(produc) {
        viewModel.getProById(produc)
        Toast.makeText(context, "id sản phẩm là ${produc}", Toast.LENGTH_SHORT).show()
    }
    product?.let { prod ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Box đầu tiên (chiếm 50% chiều cao)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f) // Chiếm 50% chiều cao
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model = prod.avatar,
                    contentDescription = prod.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.TopStart) // Căn nút ở góc trên bên trái
                        .padding(16.dp) // Khoảng cách từ cạnh
                        .background(
                            color = Color.Black.copy(alpha = 0.2f), // Nền mờ
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)

                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                        .background(color = Color.Black.copy(alpha = 0.2f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // Box thứ hai (chiếm 60% chiều cao, đè lên Box đầu tiên)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f) // Chiếm 60% chiều cao
                    .align(Alignment.BottomCenter) // Đặt ở phía dưới màn hình
                    .zIndex(0.5f) // Đặt trên Box đầu tiên
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = prod.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Price: $${prod.price}",
                        fontSize = 18.sp,
                        color = Color.Green
                    )
                    Text(
                        text = prod.infor,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(
                            0xFFF55928
                        ),
                    ), onClick = {
                        show.value = true
                    }, modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(0.7f)
                ) {
                    Text(text = "Mua hàng")
                }
            }
        }
        ShowDialog(
            showDialog = show.value,
            onDismiss = { show.value = false },
            produc
        )

    } ?: run {
        Text("Loading product details...", Modifier.padding(16.dp))
    }
}

@Composable
fun ShowDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    pro: String,
    viewModel: ProViewModel=ProViewModel()
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    val show = remember { mutableStateOf(false) }
    LaunchedEffect(pro) {
        viewModel.getProById(pro)
    }
    if (showDialog) {
        var name by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var avatar by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf(0) }
        var price by remember { mutableStateOf(0) }
        var all by remember { mutableStateOf(0) }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Điền thông tin mua hàng") },
            text = {
                product?.let { prod ->
                    Column {
                        var quantity by remember { mutableStateOf(0) }
                        AsyncImage(model = prod.avatar, contentDescription = "")
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(text = "họ và tên") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text(text = "họ và tên") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text(text = "họ và tên") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Row( verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)) {
                            IconButton(onClick = {
                                if(quantity==0){
                                    Toast.makeText(context,"Đã đạt số lượng giảm tối đa",Toast.LENGTH_SHORT).show()
                                }else{
                                    quantity--
                                }
                            }) {
                                Icon(painter = painterResource(R.drawable.minus), contentDescription = "", modifier = Modifier.size(20.dp))
                            }
                            Text(text = "${quantity}", fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 16.dp))
                            IconButton(onClick = {quantity++}) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "", modifier = Modifier.size(30.dp))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(text = "Hủy")
                }
            }
        )
    }
}
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
