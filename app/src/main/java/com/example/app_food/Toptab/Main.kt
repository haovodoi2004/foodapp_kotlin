package com.example.app_food.Toptab

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_food.Model.Oder
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
                        oderitem(item, oderViewModel, proViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun oderitem(oder: Oder, oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    val product = proViewModel.products.value[oder.id_pro]
    LaunchedEffect(oder.id_pro) {
        if (product == null) {
            proViewModel.gettProById(oder.id_pro)
        }
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product?.avatar,
                contentDescription = "Product Image",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Tên đơn hàng: ${oder.name}")
                Text(text = "Ngày: ${oder.date}")
                product?.let {
                    Text(text = "Tên sản phẩm: ${it.name}")
                    Text(text = "Loại: ${it.category}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (oder.status == 0) {
                        TextButton(onClick = {
                            val oder = Oder(
                                oder.id,
                                oder.name,
                                oder.phone,
                                oder.id_pro,
                                oder.quantity,
                                oder.all,
                                oder.address,
                                2,
                                oder.date
                            )
                            oderViewModel.updateoder(oder.id, oder)
                        }, modifier = Modifier.weight(1f)) {
                            Text(text = "Xác nhận")
                        }

                        TextButton(onClick = {
                            val oder = Oder(
                                oder.id,
                                oder.name,
                                oder.phone,
                                oder.id_pro,
                                oder.quantity,
                                oder.all,
                                oder.address,
                                1,
                                oder.date
                            )
                            oderViewModel.updateoder(oder.id, oder)
                        }, modifier = Modifier.weight(1f)) {
                            Text(text = "Hủy xác nhận", fontSize = 10.sp)
                        }
                    } else if (oder.status == 1) {

                    } else if(oder.status==2){
                        TextButton(onClick = {
                            val oder = Oder(
                                oder.id,
                                oder.name,
                                oder.phone,
                                oder.id_pro,
                                oder.quantity,
                                oder.all,
                                oder.address,
                                3,
                                oder.date
                            )
                            oderViewModel.updateoder(oder.id, oder)
                        }) {
                            Text(text = "Xác nhận")
                        }
                    }else if(oder.status==3){
                        Row {
                            TextButton(onClick = {
                                val oder = Oder(
                                    oder.id,
                                    oder.name,
                                    oder.phone,
                                    oder.id_pro,
                                    oder.quantity,
                                    oder.all,
                                    oder.address,
                                    4,
                                    oder.date
                                )
                                oderViewModel.updateoder(oder.id, oder)
                            }) {
                                Text(text = "Xác nhận")
                            }

                            TextButton(onClick = {
                                val oder = Oder(
                                    oder.id,
                                    oder.name,
                                    oder.phone,
                                    oder.id_pro,
                                    oder.quantity,
                                    oder.all,
                                    oder.address,
                                    5,
                                    oder.date
                                )
                                oderViewModel.updateoder(oder.id, oder)
                            }) {
                                Text(text = "Hủy")
                            }
                        }
                    }
                }
            }
        }
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
                        oderitem(item, viewModel, proViewModel)
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
                        oderitem(item,viewModel,proViewModel)
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
                        oderitem(item,viewModel,proViewModel)
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
                    if(item.status==5){
                        oderitem(item,oderViewModel,proViewModel)
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
                    if (item.status == 1) {
                        oderitem(item, oderViewModel = viewModel, proViewModel = proViewModel)
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