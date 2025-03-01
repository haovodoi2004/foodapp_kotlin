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
import androidx.compose.foundation.layout.Spacer

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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
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
    email: String,
    viewModel: ProViewModel,
    viewModel1: ShopcartViewModel,
    oderViewModel: OderViewModel
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    val show = remember { mutableStateOf(false) }
    var bottomSheetShow by remember { mutableStateOf(false) }
    val listShopCart by viewModel1.ShopcartItems.observeAsState(initial = emptyList())

    LaunchedEffect(listShopCart) {
        if (listShopCart.isEmpty()) {
            viewModel1.fetchShopcart()
        }
    }

    LaunchedEffect(produc) {
        viewModel.getProById(produc)
        Toast.makeText(context, "id sản phẩm là ${produc}", Toast.LENGTH_SHORT).show()
    }

    product?.let { prod ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Box đầu tiên (Hình ảnh sản phẩm)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model = prod.avatar,
                    contentDescription = prod.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Nút quay lại
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .background(color = Color.Black.copy(alpha = 0.3f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Nút giỏ hàng
                IconButton(
                    onClick = { bottomSheetShow = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(color = Color.Black.copy(alpha = 0.3f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // Bottom Sheet for Shopping Cart
            if (bottomSheetShow) {
                shopcart(
                    prod.avatar,
                    prod.name,
                    prod.quantity,
                    produc,
                    prod.price,
                    email,
                    listShopCart,
                    viewModel1
                ) {
                    bottomSheetShow = false
                }
            }

            // Box thứ hai (Thông tin sản phẩm)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .align(Alignment.BottomCenter)
                    .zIndex(0.5f)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween // Đẩy nút xuống cuối
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Đặt trọng số để chiếm không gian
                    ) {
                        item {
                            Text(
                                text = prod.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Giá: $${prod.price}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFF55928)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Số lượng: $${prod.quantity}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = prod.infor,
                                fontSize = 16.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }

                    // Button Mua hàng
                    Button(
                        onClick = { show.value = true },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            .fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF55928),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Mua hàng", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Show dialog for order confirmation
        ShowDialog(
            showDialog = show.value,
            onDismiss = { show.value = false },
            produc,
            email,
            viewModel,
            oderViewModel
        )

    } ?: run {
        Text("Loading product details...", Modifier.padding(16.dp), color = Color.Gray)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    pro: String,
    email: String,
    viewModel: ProViewModel,
    oderViewModel: OderViewModel
) {
    val product by viewModel.product.observeAsState()
    val context = LocalContext.current
    var quantity by remember { mutableStateOf(1) }
    val formattedDateTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    LaunchedEffect(pro) {
        viewModel.getProById(pro)
    }

    if (showDialog) {
        var name by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {  Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "Điền thông tin mua hàng", style = MaterialTheme.typography.titleLarge)
            } },
            text = {
                product?.let { prod ->
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        AsyncImage(
                            model = prod.avatar,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Họ và tên") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Địa chỉ nhập hàng") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Số điện thoại") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {
                                if (quantity > 1) quantity--
                            }) {
                                Icon(imageVector = Icons.Default.RemoveCircle, contentDescription = "Giảm số lượng")
                            }
                            Text(text = "$quantity", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            IconButton(onClick = {
                                if (quantity < prod.quantity.toInt()) quantity++
                            }) {
                                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Tăng số lượng")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Tổng giá: ${quantity * prod.price.toInt()} VNĐ", fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isBlank() || phone.isBlank() || address.isBlank()) {
                        Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    } else if (quantity < 1) {
                        Toast.makeText(context, "Bạn phải mua ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show()
                    } else {
                        product?.let {
                            OderPro(name, phone, it.id, quantity, quantity * it.price.toInt(), address, 0, formattedDateTime, email, oderViewModel)
                        }
                        onDismiss()
                    }
                }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Đặt hàng")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
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
    email: String,
    viewModel: OderViewModel
) {
    val oder = Oder(
        id = "",
        name = name,
        phone = phone,
        id_pro = idpro,
        quantity = quantity.toInt(),
        all = all,
        address = address,
        status = status.toInt(),
        date = date,
        emailuser = email
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
    email: String,
    list : List<Shopcart>,
    viewModel: ShopcartViewModel ,
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
   val Tag = "MyApp111111111111111";
    val Tag1 = "MyApp222222222222";
    val Tag2 = "MyApp33333333333";
    val Tag4 = "MyApp4444444444444";
    var number by remember { mutableStateOf(0) }
    var isUpdated = false
    var isupdate=false
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
                    if (sl.toInt() == 0) {
                        Toast.makeText(
                            context,
                            "Bạn hãy chọn số lượng sản phẩm muốn mua",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if(shopcartItems.isEmpty()){
                            val shopcart =
                                Shopcart("",
                                    proid,
                                    sl.toInt(),
                                    price.toInt() * sl.toInt(),
                                    email,0
                                )
                            viewModel.addshopcart(shopcart)
                            issheetopen = false
                            onDismiss()
                            Log.d(Tag, "Value of variable is: " );
                        }else {
                            for(shopcart in shopcartItems){
                                    if (shopcart.emailuser == email&&proid == shopcart.idpro && shopcart.status.toInt()==0) {
                                        Toast.makeText(
                                            context,
                                            "Sản phẩm đã có trong giỏ hàng",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isUpdated=true
                                        break
//                                        number=1
//                                        isUpdated = true
//                                        isupdate=true
//                                        viewModel.updateResponse.observe(
//                                            lifecycleOwner,
//                                            Observer { response ->
//                                                if (response != null && response.isSuccessful) {
//                                                    Toast.makeText(
//                                                        context,
//                                                        "Cập nhật thành công!",
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                } else {
//                                                    Toast.makeText(
//                                                        context,
//                                                        "Cập nhật thất bại!",
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }
//                                            })

                                    }
                            }

                            if (!isUpdated) {
                                val newShopcart = Shopcart("",
                                    proid,
                                    sl.toInt(),
                                    price.toInt() * sl.toInt(),
                                    email,0
                                )
                                viewModel.addshopcart(newShopcart)
                                issheetopen = false
                                isUpdated=false
                                onDismiss()
                                Log.d("Tag5", "Added new shopcart item")
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


