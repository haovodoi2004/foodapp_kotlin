package com.example.app_food.ScreenAdmin

import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.ViewModel.ProtypeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun productDetailAdmin(
    produc: String,
    navController: NavController,
    viewModel: ProViewModel ,
    protypeViewModel: ProtypeViewModel
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    var show by remember { mutableStateOf(false) }
    val protypelist by protypeViewModel.protypeItems.observeAsState(initial = emptyList())

    LaunchedEffect(produc) {
        viewModel.getProById(produc)
        Toast.makeText(context, "id sản phẩm là ${produc}", Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(protypelist) {
        if(protypelist.isEmpty()){
            protypeViewModel.fetchProtype()
        }
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
                    onClick = { navController.popBackStack() },
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
                Button(
                    onClick = { show = true },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFFF55928)
                    )
                ) {
                    Text(text = "Sửa thông tin")
                }
            }
        }
    } ?: run {
        Text("Loading product details...", Modifier.padding(16.dp))
    }
    if (show) {
        product?.let { productEdit(onDismiss = { show = false },viewModel, product!!,protypelist) }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenu(
    listProtype: List<Protype>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Trạng thái mở rộng menu

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } // Toggle trạng thái
    ) {
        // TextField đóng vai trò là anchor cho dropdown
        OutlinedTextField(
            value = selectedCategory.ifEmpty { "Chọn loại sản phẩm" },
            onValueChange = {},
            readOnly = true, // Không cho phép người dùng chỉnh sửa trực tiếp
            label = { Text("Category") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // Đảm bảo menu được gắn với TextField
        )
        // Kiểm tra danh sách có phần tử hay không
        if (listProtype.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                        modifier = Modifier.heightIn(max = 200.dp) // Đóng menu khi nhấn ra ngoài
            ) {
                listProtype.forEach { protype ->
                    DropdownMenuItem(
                        text = { Text(protype.name) },
                        onClick = {
                            onCategorySelected(protype.name) // Truyền giá trị đã chọn
                            expanded = false // Đóng menu
                        }
                    )
                }
            }
        } else {
            // Nếu danh sách trống, hiển thị thông báo
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Không có loại sản phẩm nào") },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun productEdit(onDismiss: () -> Unit, proViewModel: ProViewModel,product: Product,list : List<Protype>) {
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price) }
    var avatar by remember { mutableStateOf(product.avatar) }
    var infor by remember { mutableStateOf(product.infor) }
    var quantity by remember { mutableStateOf(product.quantity) }
    var selectedCategory by remember { mutableStateOf(product.category) }

    AlertDialog(onDismissRequest = { onDismiss },
        title = { Text(text = "Sửa thông tin") },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Tên sản phẩm") })
            OutlinedTextField(
                value = price.toString(),
                onValueChange = { price = it.toInt() },
                label = { Text(text = "Tên sản phẩm") })
                DropMenu(listProtype = list, selectedCategory = selectedCategory, onCategorySelected = {selectedCategory=it})
            OutlinedTextField(
                value = avatar,
                onValueChange = { avatar = it },
                label = { Text(text = "Tên sản phẩm") })
            OutlinedTextField(
                value = infor,
                onValueChange = { infor = it },
                label = { Text(text = "Tên sản phẩm") })
            OutlinedTextField(
                value = quantity.toString(),
                onValueChange = { quantity = it.toInt() },
                label = { Text(text = "Tên sản phẩm") })
    }}, dismissButton = {
        Button(onClick = {onDismiss()}) {
            Text(text = "Hủy")
        }
        }, confirmButton = {
            Button(onClick = {
                val product = Product(product.id,name,price,avatar,infor,selectedCategory,quantity)
                onDismiss()
                proViewModel.updateProduct(product.id,product)
            }) {
                Text(text = "Ok")
            }
        })
}
