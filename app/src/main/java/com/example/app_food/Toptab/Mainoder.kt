package com.example.app_food.Toptab

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product

import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Main(navController: NavController,oderViewModel: OderViewModel, proViewModel: ProViewModel,email : String) {
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
            0 -> Oder1(oderViewModel, proViewModel,navController,email)
            1 -> Oder2(oderViewModel, proViewModel,navController,email)
            2 -> Oder3(oderViewModel, proViewModel,navController,email)
            3 -> Oder4(oderViewModel, proViewModel,navController,email)
            4 -> Oder5(oderViewModel, proViewModel,navController,email)
            5 -> Oder6(oderViewModel, proViewModel,navController,email)
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Oder1(
    oderViewModel: OderViewModel,
    proViewModel: ProViewModel,
    navController: NavController,
    email: String
) {
    val oderlist by oderViewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        oderViewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Đơn hàng chờ xác nhận", textAlign = TextAlign.Center, fontSize = 30.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                startDate = newDate
            }
            SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                endDate = newDate
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    filteredOrders = oderlist.filter {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                        itemDate in startDate..endDate && it.status == 0
                    }

                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "OK")
            }

            Button(onClick = {
                filteredOrders = oderlist // Hiển thị tất cả danh sách
            }) {
                Text(text = "Hiện tất cả")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredOrders, key = { it.id }) { item ->
                if(item.status.toInt()==0) {
                    OderItem(item, oderViewModel, proViewModel, navController, email)
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallDatePickerComponent(label: String, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        selectedDate.year,
        selectedDate.monthValue - 1,
        selectedDate.dayOfMonth
    )

    OutlinedButton(
        onClick = { datePickerDialog.show() },
        modifier = Modifier
            .padding(4.dp)
            .height(36.dp)
            .width(140.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$label: ${selectedDate.toString()}", fontSize = 12.sp)
        }
    }
}

@Composable
fun OderItem(oder: Oder, oderViewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        onClick = {
            navController.navigate("oderdetail/${oder.id}/${oder.emailuser}")
        }
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
                    Text(
                        text = "\uD83C\uDFE2 Tên đơn hàng: ${oder.id}",
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


@SuppressLint("NewApi")
@Composable
fun Oder2(viewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
    val oderlist by viewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        viewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng đã xác nhân", textAlign = TextAlign.Center, fontSize = 30.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                    startDate = newDate
                }
                SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                    endDate = newDate
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        filteredOrders = oderlist.filter {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                            itemDate in startDate..endDate && it.status == 0
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }

                Button(onClick = {
                    filteredOrders = oderlist // Hiển thị tất cả danh sách
                }) {
                    Text(text = "Hiện tất cả")
                }
            }
            LazyColumn {
                items(filteredOrders, key = { it.id }) { item ->
                    if (item.status == 2) {
                        OderItem(item, viewModel, proViewModel,navController,email)
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Oder3(viewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
    val oderlist by viewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        viewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng đang vận chuyển", textAlign = TextAlign.Center, fontSize = 30.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                    startDate = newDate
                }
                SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                    endDate = newDate
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        filteredOrders = oderlist.filter {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                            itemDate in startDate..endDate && it.status == 0
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }

                Button(onClick = {
                    filteredOrders = oderlist // Hiển thị tất cả danh sách
                }) {
                    Text(text = "Hiện tất cả")
                }
            }
            LazyColumn {
                items(filteredOrders, key = {it.id}){
                    item->
                    if(item.status==3){
                        OderItem(item,viewModel,proViewModel,navController,email)
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Oder4(viewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
    val oderlist by viewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        viewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng giao thành công", textAlign = TextAlign.Center, fontSize = 30.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                    startDate = newDate
                }
                SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                    endDate = newDate
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        filteredOrders = oderlist.filter {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                            itemDate in startDate..endDate && it.status == 0
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }

                Button(onClick = {
                    filteredOrders = oderlist // Hiển thị tất cả danh sách
                }) {
                    Text(text = "Hiện tất cả")
                }
            }
            LazyColumn {
                items(filteredOrders, key = {it.id}){
                    item->
                    if(item.status==4){
                        OderItem(item,viewModel,proViewModel,navController,email)
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Oder5(oderViewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
    val oderlist by oderViewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        oderViewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                    startDate = newDate
                }
                SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                    endDate = newDate
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        filteredOrders = oderlist.filter {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                            itemDate in startDate..endDate && it.status == 0
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }

                Button(onClick = {
                    filteredOrders = oderlist // Hiển thị tất cả danh sách
                }) {
                    Text(text = "Hiện tất cả")
                }
            }
            LazyColumn {
                items(filteredOrders, key = {it.id}){
                    item->
                    if(item.status==1){
                        OderItem(item,oderViewModel,proViewModel,navController,email)
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Oder6(viewModel: OderViewModel, proViewModel: ProViewModel,navController: NavController,email: String) {
    val oderlist by viewModel.oder.observeAsState(initial = emptyList())
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var filteredOrders by remember { mutableStateOf(oderlist) }

    LaunchedEffect(Unit) {
        viewModel.fetchoder()
    }

    // Khi oderlist thay đổi, cập nhật filteredOrders
    LaunchedEffect(oderlist) {
        filteredOrders = oderlist
    }
    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đơn hàng giao thất bại", textAlign = TextAlign.Center, fontSize = 30.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallDatePickerComponent("Ngày bắt đầu", startDate) { newDate ->
                    startDate = newDate
                }
                SmallDatePickerComponent("Ngày kết thúc", endDate) { newDate ->
                    endDate = newDate
                }
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        filteredOrders = oderlist.filter {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val itemDate = LocalDateTime.parse(it.date, formatter).toLocalDate()
                            itemDate in startDate..endDate && it.status == 0
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }

                Button(onClick = {
                    filteredOrders = oderlist // Hiển thị tất cả danh sách
                }) {
                    Text(text = "Hiện tất cả")
                }
            }
            LazyColumn {
                items(filteredOrders, key = { it.id }) { item ->
                    if (item.status == 5) {
                        OderItem(item, oderViewModel = viewModel, proViewModel = proViewModel,navController,email)
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