package com.example.app_food.Bottombar

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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

@Composable
fun ShopCartItem(shopcart: Shopcart,viewModel: ProViewModel= ProViewModel()){
    val pro by viewModel.product.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(shopcart.idpro) {
        viewModel.getProById(shopcart.idpro)
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screeenWeight= LocalConfiguration.current.screenWidthDp.dp
    var sl by remember { mutableStateOf(0) }
    var show by remember { mutableStateOf(false) }
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
                AsyncImage(model = pr.avatar, contentDescription = "", modifier = Modifier
                    .height(screenHeight * 0.15f)
                    .width(screeenWeight * 0.3f), contentScale = ContentScale.Crop)
                Column() {
                    Text(text = pr.name)
                    Text(text = (pr.price.toInt()*shopcart.quantity.toInt()).toString())
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            sl=sl--
                            if(sl+shopcart.quantity.toInt()==0){
                                Toast.makeText(context,"Đã đạt đến mưcs giảm tối đa",Toast.LENGTH_SHORT).show()
                            }else{
                                shopcart.quantity.toInt() + sl
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
                        OpenDialogShop (show = true, onDissMiss = {show=false})
                    }
                }
            }
        }
    }
}

@Composable
fun OpenDialogShop(show: Boolean, onDissMiss: () -> Unit) {
    Text(text = "Đặt hàng")
    AlertDialog(
        onDismissRequest = onDissMiss,
        title = { Text(text = "Thông tin mua hàng") },
        text = {

        },
        confirmButton = {},
        dismissButton = {})
}