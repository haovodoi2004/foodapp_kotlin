package com.example.app_food.Bottombar

import android.app.AlertDialog
import android.os.Build
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product
import com.example.app_food.Screen.OderPro
import com.example.app_food.ViewModel.OderViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Shopcart(navController: NavController,viewModel: ShopcartViewModel= ShopcartViewModel()){
    val shopcartitems by viewModel.ShopcartItems.observeAsState(initial = emptyList())
    if(shopcartitems.size==0){
        LaunchedEffect(Unit) {
            viewModel.fetchShopcart()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Shop Cart", fontSize = 30.sp)
            if(shopcartitems.isEmpty()){
                Text("đang load dữ liệu")
            }else{
                LazyColumn() {
                    items(shopcartitems){shopcartitem->
                        ShopCartItem(shopcartitem)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopCartItem(shopcart: Shopcart,viewModel: ProViewModel= ProViewModel(),viewModelshopcart: ShopcartViewModel=ShopcartViewModel()){
    val pro by viewModel.product.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(shopcart.idpro) {
        viewModel.getProById(shopcart.idpro)
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screeenWeight= LocalConfiguration.current.screenWidthDp.dp
    var sl by remember { mutableStateOf(0) }
    var show by remember { mutableStateOf(false) }
    var showUpdateShopcart by remember { mutableStateOf(false) }

    pro?.let{pr->
        Card(
            modifier = Modifier
                .fillMaxHeight(0.3f)  // Chiều cao bằng 4/10 chiều dài của màn hình
                .width(screeenWeight * 1f)
                .padding(start = 8.dp, end = 8.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ){

        Row(){
            var allprice by remember { mutableStateOf(pr.price.toInt()*(sl+shopcart.quantity.toInt())) }
                AsyncImage(model = pr.avatar, contentDescription = "", modifier = Modifier
                    .height(screenHeight * 0.15f)
                    .width(screeenWeight * 0.3f), contentScale = ContentScale.Crop)
                Column() {
                    Text(text = pr.name)
                    Text(text = allprice.toString())
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            sl--
                            if(sl+shopcart.quantity.toInt()==0){
                                Toast.makeText(context,"Đã đạt đến mưcs giảm tối đa",Toast.LENGTH_SHORT).show()
                            }else{
                                showUpdateShopcart=true
                                sl+shopcart.quantity.toInt()
                               allprice=(sl+shopcart.quantity.toInt())*pr.price.toInt()
                            }

                        }) {
                            Icon(painter = painterResource(R.drawable.minus), contentDescription = "")
                        }
                        Text(text = "${shopcart.quantity.toInt()+sl.toInt()}")
                        IconButton(onClick = {
                            sl=sl++
                            Toast.makeText(context,"${pr.quantity} va ${sl+shopcart.quantity.toInt()}",Toast.LENGTH_SHORT).show()
                            if(sl+shopcart.quantity.toInt()==pr.quantity.toInt()){
                                Toast.makeText(context,"Đã đạt đến mưcs tối đa",Toast.LENGTH_SHORT).show()
                            }else{
                                shopcart.quantity.toInt()+sl++
                                allprice=(sl+shopcart.quantity.toInt())*pr.price.toInt()
                               showUpdateShopcart=true
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    }
                    Button(onClick = {
                        show=true

                    }) {
                        Text(text = "Mua hàng")
                    }
                    if(show){

                        OpenDialogShop (show = true, onDissMiss = {show=false},pr,shopcart)
                    }
                }
            if(showUpdateShopcart){
                UpdateShopcart(pr,shopcart,sl)
                showUpdateShopcart=false
            }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenDialogShop(show: Boolean, onDissMiss: () -> Unit,pro : Product,shopcart : Shopcart,oderviewmodel : OderViewModel= OderViewModel(),ProViewModel : ProViewModel=ProViewModel(),shopcartViewModel : ShopcartViewModel = ShopcartViewModel()) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
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
                    onDissMiss()
                    val oder = Oder("",name,phone,pro.id,shopcart.quantity.toInt(),shopcart.all.toInt(),address,0,formattedDateTime)
                    oderviewmodel.addOder(oder)
                    val pro = Product(pro.id,pro.name,pro.price.toInt(),pro.avatar,pro.infor,pro.category,pro.quantity.toInt()-shopcart.quantity.toInt(),0)
                    ProViewModel.updateProduct(pro.id,pro)
                    shopcartViewModel.deleteShopcart(pro.id)

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

@Composable
fun UpdateShopcart(pro : Product,shopcart : Shopcart,sl : Int,viewModel: ShopcartViewModel= ShopcartViewModel()){
    val lifecycle = LocalLifecycleOwner.current
    shopcart.quantity.toInt() + sl
    viewModel.updateResponse.observe(lifecycle, Observer {
            respone->
        if(respone!=null && respone.isSuccessful){

        }else{

        }
    })
    val shopcart= Shopcart(shopcart.idpro,sl+shopcart.quantity.toInt(),(sl+shopcart.quantity.toInt())*pro.price.toInt())
    viewModel.updateData(pro.id,shopcart)
}