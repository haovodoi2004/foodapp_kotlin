package com.example.app_food.ScreenAdmin

import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Shopcart
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ShopcartViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun productDetailAdmin(
    navController: NavController,
    produc: String,
    viewModel: ProViewModel = ProViewModel()
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current


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

            }
        }
    } ?: run {
        Text("Loading product details...", Modifier.padding(16.dp))
    }
}

