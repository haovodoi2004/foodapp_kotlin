package com.example.app_food.Toptab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.app_food.R
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product
import com.example.app_food.ScreenAdmin.oderItem
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel

@Composable
fun Main(oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    val tabItem = listOf(
        tab(

            unselected = R.drawable.file,
            selected = R.drawable.file
        ),
        tab(

            unselected = R.drawable.order,
            selected = R.drawable.order
        ),
        tab(

            unselected = R.drawable.delivery,
            selected = R.drawable.delivery
        ),
        tab(

            unselected = R.drawable.fast,
            selected = R.drawable.fast
        ),
        tab(

            unselected = R.drawable.file1,
            selected = R.drawable.file1
        ),
        tab(
            unselected = R.drawable.delivery1,
            selected = R.drawable.delivery1
        )
    )


    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItem.forEachIndexed { index, item ->
                Tab(selected = index == selectedTabIndex, onClick = {
                    selectedTabIndex = index
                },
                    modifier = Modifier.weight(1f),

                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (index == selectedTabIndex) {
                                    item.selected
                                } else item.unselected
                            ), modifier = Modifier.size(30.dp),
                            contentDescription = ""
                        )
                    })
            }
        }
        when (selectedTabIndex) {
            0 -> Oder1(oderViewModel, proViewModel)
            1 -> Oder2(oderViewModel, proViewModel)
            2 -> Oder3(oderViewModel, proViewModel)
            3 -> Oder4(oderViewModel, proViewModel)
            4 -> Oder5(oderViewModel, proViewModel)
            5 -> Oder6(oderViewModel, proViewModel)
        }
    }
}

@Composable
fun Oder1(oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val oderlist by oderViewModel.oder.observeAsState(initial = emptyList())
        if (oderlist.isEmpty()) {
            oderViewModel.fetchoder()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng chờ xác nhận", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(oderlist, key = { it.id }) { item ->
                    if (item.status == 0) {
                        OderItem(item, oderViewModel, proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun OderItem(oder: Oder, oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    val product = proViewModel.products.value[oder.id_pro]

    LaunchedEffect(oder.id_pro) {
        if (product == null) {
            proViewModel.gettProById(oder.id_pro)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column {
            Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                // Product Image
                AsyncImage(
                    model = product?.avatar,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Order Information
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "\uD83D\uDCC5 Ngày: ${oder.date}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "\uD83C\uDFE2 Tên đơn hàng: ${oder.name}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    product?.let {
                        Text(
                            text = "\uD83D\uDCDA Tên sản phẩm: ${it.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "\uD83C\uDFE6 Loại: ${it.category}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }


                }
            }
                Row(horizontalArrangement = Arrangement.Center) {
                    // Buttons for Order Actions
                    when (oder.status) {
                        0 -> {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                ActionButton(
                                    text = "Xác nhận",
                                    color = Color(0xFF4CAF50),
                                    onClick = {
                                        val updatedOder = oder.copy(status = 2)
                                        oderViewModel.updateoder(oder.id, updatedOder)
                                    }
                                )
                                ActionButton(
                                    text = "Hủy xác nhận",
                                    color = Color(0xFFF44336),
                                    onClick = {
                                        val updatedOder = oder.copy(status = 1)
                                        oderViewModel.updateoder(oder.id, updatedOder)

                                        product?.let {
                                            val updatedProduct = it.copy(
                                                quantity = it.quantity.toInt() + oder.quantity.toInt()
                                            )
                                            proViewModel.updateProduct(oder.id_pro, updatedProduct)
                                        }
                                    }
                                )
                            }
                        }

                        2 -> {
                            ActionButton(
                                text = "Xác nhận",
                                color = Color(0xFF03A9F4),
                                onClick = {
                                    val updatedOder = oder.copy(status = 3)
                                    oderViewModel.updateoder(oder.id, updatedOder)
                                }
                            )
                        }

                        3 -> {
                            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                                ActionButton(
                                    text = "Hoàn tất",
                                    color = Color(0xFF8BC34A),
                                    onClick = {
                                        val updatedOder = oder.copy(status = 4)
                                        oderViewModel.updateoder(oder.id, updatedOder)
                                    }
                                )
                                ActionButton(
                                    text = "Hủy",
                                    color = Color(0xFFFF5722),
                                    onClick = {
                                        val updatedOder = oder.copy(status = 5)
                                        oderViewModel.updateoder(oder.id, updatedOder)
                                    }
                                )
                            }
                        }
                    }
                }

        }
    }
}

@Composable
fun ActionButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .padding(horizontal = 4.dp)

    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}



@Composable
fun Oder2(viewModel: OderViewModel, proViewModel: ProViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val oderlist by viewModel.oder.observeAsState(initial = emptyList())
        LaunchedEffect(oderlist) {
            if (oderlist.isEmpty()) {
                viewModel.fetchoder()
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng đã xác nhân", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn {
                items(oderlist, key = { it.id }) { item ->
                    if (item.status == 2) {
                        OderItem(item, viewModel, proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Oder3(viewModel: OderViewModel, proViewModel: ProViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val oderlist by viewModel.oder.observeAsState(initial = emptyList())
        LaunchedEffect(oderlist) {
            if(oderlist.isEmpty()){
                viewModel.fetchoder()
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng đang vận chuyển", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn {
                items(oderlist, key = {it.id}){
                    item->
                    if(item.status==3){
                        OderItem(item,viewModel,proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Oder4(viewModel: OderViewModel, proViewModel: ProViewModel) {
    val oderlist by viewModel.oder.observeAsState(initial = emptyList())
    LaunchedEffect(oderlist) {
        if(oderlist.isEmpty()){
            viewModel.fetchoder()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng giao thành công", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn {
                items(oderlist, key = {it.id}){
                    item->
                    if(item.status==4){
                        OderItem(item,viewModel,proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Oder5(oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val oderlist by oderViewModel.oder.observeAsState(initial = emptyList())
        LaunchedEffect(oderlist) {
            if(oderlist.isEmpty()){
                oderViewModel.fetchoder()
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng bị hủy", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn {
                items(oderlist, key = {it.id}){
                    item->
                    if(item.status==1){
                        OderItem(item,oderViewModel,proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Oder6(viewModel: OderViewModel, proViewModel: ProViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val oderlist by viewModel.oder.observeAsState(initial = emptyList())
        LaunchedEffect(oderlist) {
            if (oderlist.isEmpty()) {
                viewModel.fetchoder()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng giao thất bại", textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn {
                items(oderlist, key = { it.id }) { item ->
                    if (item.status == 5) {
                        OderItem(item, oderViewModel = viewModel, proViewModel = proViewModel)
                    }
                }
            }
        }
    }
}

data class tab(
    val unselected: Int,
    val selected: Int
)