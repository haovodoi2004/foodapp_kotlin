package com.example.app_food.Screen

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
fun ProductDetail(
    navController: NavController,
    produc: String,
    viewModel: ProViewModel = ProViewModel()
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    val show = remember { mutableStateOf(false) }
    var bottomshettshow by remember { mutableStateOf(false) }

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
                    onClick = {
                        bottomshettshow = true // Cập nhật trạng thái mở
                    },
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
            if (bottomshettshow) {
                shopcart(prod.avatar, prod.name, prod.quantity, produc, prod.price) {
                    bottomshettshow = false
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    pro: String,
    viewModel: ProViewModel = ProViewModel()
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    val show = remember { mutableStateOf(false) }
    var productQuantity by remember { mutableStateOf(0) }
    var proid by remember { mutableStateOf("") }
    val formattedDateTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    LaunchedEffect(pro) {
        viewModel.getProById(pro)
        product?.let {
            productQuantity = it.quantity.toInt() // Lưu số lượng sản phẩm

        }
    }
    if (showDialog) {
        var name by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf(0) }
        var all by remember { mutableStateOf(0) }
        var price by remember { mutableStateOf(0) }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Điền thông tin mua hàng") },
            text = {
                product?.let { prod ->
                    Column {
                        proid = prod.id
                        price = prod.price.toInt()
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
                            label = { Text(text = "Địa chỉ nhập hàng") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text(text = "Số điện thoại") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            IconButton(onClick = {
                                if (quantity == 0) {
                                    Toast.makeText(
                                        context,
                                        "Đã đạt số lượng giảm tối đa",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    quantity--
                                }
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.minus),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "${quantity}", fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            IconButton(onClick = {
                                if (quantity < prod.quantity.toInt()) {
                                    quantity++
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Đã đạt giới hạn số lượng sản phẩm tối đa là  ${productQuantity}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Text("Tổng giá là ${quantity * prod.price.toInt()}")
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isBlank() || phone.isBlank() || address.isBlank()) {
                        Toast.makeText(
                            context,
                            "vui lòng điền đầy đủ thông tin",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (quantity == 0) {
                            Toast.makeText(
                                context,
                                "Bạn phải mua ít nhất 1 sản phẩm",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            OderPro(
                                name,
                                phone,
                                proid,
                                quantity,
                                quantity * price,
                                address,
                                0,
                                formattedDateTime
                            )
                        }
                    }

                    onDismiss()
                }) {
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


fun OderPro(
    name: String,
    phone: String,
    idpro: String,
    quantity: Int,
    all: Int,
    address: String,
    status: Int,
    date: String,
    viewModel: OderViewModel = OderViewModel()
) {
    val oder = Oder(
        name = name,
        phone = phone,
        id_pro = idpro,
        quantity = quantity.toInt(),
        all = all,
        address = address,
        status = status.toInt(),
        date = date
    )
    viewModel.addOder(oder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shopcart(
    avatar: String,
    name: String,
    quantity: Number,
    proid: String,
    price: Number,
    viewModel: ShopcartViewModel = ShopcartViewModel(),
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var issheetopen by rememberSaveable {
        mutableStateOf(true)
    }
    var sl by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val shopcartItems by viewModel.ShopcartItems.observeAsState(initial = emptyList())
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        if (shopcartItems.isEmpty()) {
            viewModel.fetchShopcart()
        }
    }
    if (issheetopen) {
        ModalBottomSheet(
            onDismissRequest = {
                issheetopen = false
                onDismiss()
            },
            sheetState = sheetState
        ) {
            // Nội dung của BottomSheet
            Row {
                AsyncImage(
                    model = avatar,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(0.3f)
                )
                Column {
                    Text(text = "${name}", fontSize = 30.sp)
                    Text(text = "Số lượng còn : ${quantity}", fontSize = 20.sp)
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (sl == 0) {
                        Toast.makeText(context, "Đã đạt số lương giảm tối đa", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        sl--
                    }
                }) {
                    Image(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(text = "${sl}")
                IconButton(onClick = {
                    if (sl == quantity.toInt()) {
                        Toast.makeText(
                            context,
                            "Đã đạt số lương tối đa có thể mua",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sl++
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(
                        0xFFF55928
                    ),
                ), onClick = {
                    Log.d("Debug", "ShopcartItems size: ${shopcartItems.size}")

                    if (sl.toInt() == 0) {
                        Toast.makeText(
                            context,
                            "Bạn hãy chọn số lượng sản phẩm muốn mua",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (shopcartItems.size == 0) {
                            val shopcart = Shopcart(proid, sl.toInt(), price.toInt() * sl.toInt())
                            viewModel.addshopcart(shopcart)
                            issheetopen = false
                            onDismiss()
                        } else {


                            shopcartItems.forEach { shopcart ->

                                if (proid == shopcart.idpro) {
                                    viewModel.updateResponse.observe(
                                        lifecycleOwner,
                                        Observer { response ->
                                            if (response != null && response.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Cập nhật thành công!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Cập nhật thất bại!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })

                                    val shopcart = Shopcart(
                                        proid,
                                        shopcart.quantity.toInt() + sl.toInt(),
                                        price.toInt() + sl.toInt() * price.toInt()
                                    )
                                    viewModel.updateData(proid, shopcart)
                                    issheetopen = false
                                    onDismiss()
                                } else {
                                    val shopcart =
                                        Shopcart(proid, sl.toInt(), price.toInt() * sl.toInt())
                                    viewModel.addshopcart(shopcart)
                                    issheetopen = false
                                    onDismiss()
//                            Toast.makeText(context,"${proid} và ${shopcart.idpro}",Toast.LENGTH_SHORT).show()
                                }
                                Log.d("Debug", "proid: $proid, shopcart.idpro: ${shopcart.idpro}")

                            }
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Text(text = "Mua hàng")
            }
        }
    }
}


