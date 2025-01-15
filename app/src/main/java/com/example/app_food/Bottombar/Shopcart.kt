package com.example.app_food.Bottombar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.Shopcart
import com.example.app_food.R
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ShopcartViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product
import com.example.app_food.Screen.OderPro
import com.example.app_food.ViewModel.OderViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Shopcart(modifier: Modifier,navController: NavController,email :String,viewModel: ShopcartViewModel,proViewModel: ProViewModel,oderviewmodel: OderViewModel){
    val shopcartitems by viewModel.ShopcartItems.observeAsState(initial = emptyList())

        LaunchedEffect(shopcartitems) {
            if(shopcartitems.isEmpty()) {
                viewModel.fetchShopcart()
                Log.e("Compose", "Shopcart items: $shopcartitems")
            }
        }

    Box(modifier = modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Shop Cart", fontSize = 30.sp, textAlign = TextAlign.Center)
            if(shopcartitems.isEmpty()){
                Text("đang load dữ liệu")
            }else{
                LazyColumn() {
                    items(shopcartitems, key = {it.id}){shopcartitem->
                        Log.e("LazyColumn", "Shopcart emailuser: ${shopcartitem.quantity}, Filtered email: $email")
                        if(shopcartitem.emailuser==email && shopcartitem.status.toInt()==0) {
                            ShopCartItem(shopcartitem, email,proViewModel,viewModel,oderviewmodel)
                        }
                    }
                }
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ShopCartItem(shopcart: Shopcart,email: String,proviewModel: ProViewModel,viewModel: ShopcartViewModel,oderviewmodel: OderViewModel){
//    val context = LocalContext.current
//
//    val produc =proviewModel.pro.observeAsState()
//    val product = proviewModel.products.value[shopcart.idpro]
//    LaunchedEffect(shopcart.idpro) {
//        if (product == null) {
//            proviewModel.gettProById(shopcart.idpro)
//        }
//    }
//    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
//    val screeenWeight= LocalConfiguration.current.screenWidthDp.dp
//    var sl by remember { mutableStateOf(0) }
//    var show by remember { mutableStateOf(false) }
//    var show1 by remember { mutableStateOf(false) }
//
//
//    product?.let{pr->
//        Card(
//            modifier = Modifier
//                .fillMaxHeight(0.3f)  // Chiều cao bằng 4/10 chiều dài của màn hình
//                .width(screeenWeight * 1f)
//                .padding(start = 8.dp, end = 8.dp),
//            elevation = CardDefaults.elevatedCardElevation(8.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White), onClick = {
//
//            }
//
//        ){
//
//        Row(){
//            var allprice by remember { mutableStateOf(pr.price.toInt()*(sl+shopcart.quantity.toInt())) }
//                AsyncImage(model = pr.avatar, contentDescription = "", modifier = Modifier
//                    .height(screenHeight * 0.15f)
//                    .width(screeenWeight * 0.3f), contentScale = ContentScale.Crop)
//                Column() {
//                    Text(text = pr.name)
//                    Text(text = pr.quantity.toString())
//                    Text(text = allprice.toString())
//                    Text(text = shopcart.emailuser)
//                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//                        IconButton(onClick = {
//                            sl--
//                            if(sl+shopcart.quantity.toInt()==0){
//                                val shopcartt=Shopcart(shopcart.id,shopcart.idpro,0,0,shopcart.emailuser,0)
//                                viewModel.updateData(shopcart.id,shopcartt)
//                                Toast.makeText(context,"Đã đạt đến mưcs giảm tối đa",Toast.LENGTH_SHORT).show()
//                            }else{
////                                showUpdateShopcart=true
//                                sl+shopcart.quantity.toInt()
//                               allprice=(sl+shopcart.quantity.toInt())*pr.price.toInt()
//                                val shopcartt=Shopcart(shopcart.id,shopcart.idpro,sl+shopcart.quantity.toInt(),allprice,shopcart.emailuser,0)
//                                viewModel.updateData(shopcart.id,shopcartt)
//                                sl=0
//                            }
//
//                        }) {
//                            Icon(painter = painterResource(R.drawable.minus), contentDescription = "")
//                        }
//                        Text(text = "${shopcart.quantity.toInt()+sl.toInt()}")
//                        IconButton(onClick = {
//                            sl=sl++
//                            Toast.makeText(context,"${pr.quantity} va ${sl+shopcart.quantity.toInt()}",Toast.LENGTH_SHORT).show()
//                            if(sl+shopcart.quantity.toInt()-1==pr.quantity.toInt()){
//                                Toast.makeText(context,"Đã đạt đến mưcs tối đa",Toast.LENGTH_SHORT).show()
//                                sl+shopcart.quantity.toInt()
//                                allprice=(sl+shopcart.quantity.toInt())*pr.price.toInt()
//                                val shopcartt=Shopcart(shopcart.id,shopcart.idpro,sl+shopcart.quantity.toInt(),allprice,shopcart.emailuser,0)
//                                viewModel.updateData(shopcart.id,shopcartt)
//                            }else{
//                                shopcart.quantity.toInt()+sl++
//                                allprice=(sl+shopcart.quantity.toInt())*pr.price.toInt()
//                                val shopcartt=Shopcart(shopcart.id,shopcart.idpro,sl+shopcart.quantity.toInt(),allprice,shopcart.emailuser,0)
//                                viewModel.updateData(shopcart.id,shopcartt)
////                               showUpdateShopcart=true
//                                sl=0
//                            }
//                        }) {
//                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
//                        }
//                    }
//                    Row {
//                        Button(onClick = {
//                            show = true
//
//                        }) {
//                            Text(text = "Mua hàng")
//                        }
//
//                        Button(onClick = {
//                            Toast.makeText(context,"Số lượng sản phẩm ${pr.quantity} và ${shopcart.quantity}",Toast.LENGTH_LONG).show()
////                            show1=true
//                        }) {
//                            Text(text = "Hủy")
//                        }
//                    }
//                    if(show){
//                        OpenDialogShop (show = true, onDissMiss = {show=false},pr,shopcart,email, oderviewmodel = oderviewmodel,proviewModel,viewModel)
//                    }
//                    if(show1){
//                        DeleteDialog(onDismiss = {show1=false},pr,viewModel)
//                    }
//                }
////            if(showUpdateShopcart){
////                UpdateShopcart(pr,shopcart,sl,email, ShopcartViewModel())
////                showUpdateShopcart=false
////            }
//            }
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopCartItem(
    shopcart: Shopcart,
    email: String,
    proviewModel: ProViewModel,
    viewModel: ShopcartViewModel,
    oderViewModel: OderViewModel
) {
    val context = LocalContext.current
    val product = proviewModel.products.value[shopcart.idpro]
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var sl by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(shopcart.idpro) {
        if (product == null) {
            proviewModel.gettProById(shopcart.idpro)
        }
    }

    product?.let { pr ->
        Box( modifier = Modifier
            .fillMaxWidth(), // Chiếm toàn bộ kích thước màn hình
            contentAlignment = Alignment.Center ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .width(screenWidth * 0.95f)
                    .padding(8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column() {
                    Row(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = pr.avatar,
                            contentDescription = pr.name,
                            modifier = Modifier
                                .height(screenHeight * 0.15f)
                                .width(screenWidth * 0.3f)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = pr.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Available: ${pr.quantity}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            var totalPrice by remember { mutableStateOf(pr.price.toInt() * (sl + shopcart.quantity.toInt())) }
                            Text(
                                text = "Total: $$totalPrice",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Green
                            )

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                // Decrease Quantity Button
                                IconButton(
                                    onClick = {
                                        if (sl+ shopcart.quantity.toInt() > 0) {
                                            sl--
                                            totalPrice =
                                                (sl + shopcart.quantity.toInt()) * pr.price.toInt()
                                            val updatedShopCart = Shopcart(
                                                shopcart.id,
                                                shopcart.idpro,
                                                sl + shopcart.quantity.toInt(),
                                                totalPrice,
                                                shopcart.emailuser,
                                                0
                                            )
                                            viewModel.updateData(shopcart.id, updatedShopCart)
                                            sl=0
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Minimum quantity reached",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease Quantity"
                                    )
                                }

                                Text(
                                    text = "${shopcart.quantity.toInt() + sl}",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                // Increase Quantity Button
                                IconButton(
                                    onClick = {
                                        if (sl + shopcart.quantity.toInt() < pr.quantity.toInt()) {
                                            sl++
                                            totalPrice =
                                                (sl + shopcart.quantity.toInt()) * pr.price.toInt()
                                            val updatedShopCart = Shopcart(
                                                shopcart.id,
                                                shopcart.idpro,
                                                sl + shopcart.quantity.toInt(),
                                                totalPrice,
                                                shopcart.emailuser,
                                                0
                                            )
                                            viewModel.updateData(shopcart.id, updatedShopCart)
                                            sl=0
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Maximum quantity reached",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase Quantity"
                                    )
                                }
                            }
                        }

                        // Show Dialog for Purchase
//                    if(show){
////                        OpenDialogShop (show = true, onDissMiss = {show=false},pr,shopcart,email, oderviewmodel = oderviewmodel,proviewModel,viewModel)
////                    }
                        if (showDialog) {
                            OpenDialogShop(
                                show = true,
                                onDissMiss = { showDialog = false },
                                pr,
                                shopcart,
                                email,
                                oderViewModel,
                                proviewModel,
                                viewModel
                            )
                        }

                        // Show Dialog for Cancellation
                        if (showCancelDialog) {
                            DeleteDialog(
                                onDismiss = { showCancelDialog = false },
                                pr,
                                shopcart,
                                viewModel
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Mua hàng Button
                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF55928))
                        ) {
                            Text(
                                text = "Mua hàng",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Hủy Button
                        Button(
                            onClick = { showCancelDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text(text = "Hủy", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(onDismiss:()->Unit,pro : Product,shopcart: Shopcart,shopcartViewModel: ShopcartViewModel){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Box(modifier = Modifier.fillMaxWidth()){
                Text(text = "Thông báo")
            }
        },
        text = {
            Text(text = "Bạn có muốn xóa ${pro.name} khỏi giỏ hàng")
        },
        confirmButton = {
            Button(onClick = {
                shopcartViewModel.deleteShopcart(shopcart.id)
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton ={
            Button(onClick = {onDismiss()}) {
                Text(text = "Hủy")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenDialogShop(show: Boolean, onDissMiss: () -> Unit,pro : Product,shopcart : Shopcart,email: String,oderviewmodel : OderViewModel,ProViewModel : ProViewModel,shopcartViewModel : ShopcartViewModel ) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val context = LocalContext.current
    val deleteJob = remember { mutableStateOf(false) }
    val formattedDateTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    AlertDialog(
        onDismissRequest = onDissMiss,
        title = {  Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Thông tin mua hàng",
                modifier = Modifier.align(Alignment.Center), // Căn giữa cả chiều ngang và dọc
                style = MaterialTheme.typography.titleMedium // Tùy chỉnh kiểu chữ (nếu muốn)
            )
        } },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Nhập họ và tên") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(text = "Nhập số điện thoại") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = "Nhập địa chỉ giao hàng") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.size(20.dp))

            }
        },

        confirmButton = {
            TextButton(
                onClick = {
                    val oder = Oder("",name,phone,pro.id,shopcart.quantity.toInt(),shopcart.all.toInt(),address,0,formattedDateTime, emailuser = email)
                    oderviewmodel.addOder(oder)
                    Log.d("Tag44444", "${pro.quantity}")
                    val proo = Product(pro.id,pro.name,pro.price.toInt(),pro.avatar,pro.infor,pro.category,pro.quantity.toInt()-shopcart.quantity.toInt(),0)
                    ProViewModel.updateProduct(pro.id, proo)
                    Log.d("Tag555555555", "${proo.quantity}")
                    ProViewModel.fetchProduct()
                    val shopcart=Shopcart(shopcart.id,shopcart.idpro,shopcart.quantity,shopcart.all,shopcart.emailuser,1)
                    shopcartViewModel.updateData(shopcart.id,shopcart)
                    Toast.makeText(context," số lượng là ${pro.quantity.toInt()} giỏ hàng là ${shopcart.quantity.toInt()} = ${pro.quantity.toInt()-shopcart.quantity.toInt()}",Toast.LENGTH_LONG).show()
                    onDissMiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = Color(
                        0xFFF55928
                    )
                ),
            ) {
                Text(text = "Đặt hàng")
            }

        },
        dismissButton = {
            Button(onClick = onDissMiss, colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color(0xFFF55928))) {
                Text(text = "Hủy")
            }
        })
}

