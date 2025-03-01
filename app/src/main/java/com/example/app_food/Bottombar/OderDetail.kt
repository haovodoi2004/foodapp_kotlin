package com.example.app_food.Bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel

@Composable
fun OderDetail(
    navController: NavController,
    oderViewModel: OderViewModel,
    proViewModel: ProViewModel,
    oderid: String,
    email: String
) {
    val oder by oderViewModel.dataoder.observeAsState()
    val pro by proViewModel.product.observeAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(oderid) {
        oderViewModel.getOderById(oderid)
    }

    oder?.let { oderitem ->
        LaunchedEffect(oderitem.id_pro) {
            proViewModel.products.value[oderitem.id_pro]
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Thông tin đơn hàng",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    pro?.let { pr ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = pr.avatar,
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .size(screenWidth * 0.5f)
                                    .aspectRatio(1f)
                            )
                        }
                        OrderDetailItem("Tên đơn hàng", pr.name, Icons.Default.ShoppingBag)
                    }
                    OrderDetailItem("Ngày đặt", oderitem.date ?: "Không có dữ liệu", Icons.Default.CalendarToday)
                    OrderDetailItem("Email", oderitem.emailuser ?: "Không có dữ liệu", Icons.Default.Email)
                    OrderDetailItem("Tổng tiền", "${oderitem.all ?: 0} VNĐ", Icons.Default.AttachMoney)
                    OrderDetailItem("Số lượng", oderitem.quantity?.toString() ?: "0", Icons.Default.FormatListNumbered)
                    OrderDetailItem("Địa chỉ", oderitem.address ?: "Không có địa chỉ", Icons.Default.LocationOn)
                    OrderDetailItem("Số điện thoại", oderitem.phone ?: "Không có số điện thoại", Icons.Default.Phone)


                    Text(
                        text = "Trạng thái: ${getStatusText(oderitem.status.toInt())}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Blue,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }?: run {
        Text("Không tìm thấy đơn hàng", fontSize = 20.sp, color = Color.Red)
    }
}

@Composable
fun OrderDetailItem(label: String, value: String?, icon: ImageVector) {
    val displayValue = value ?: "Không có dữ liệu" // Xử lý nếu value bị null
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = displayValue, fontSize = 16.sp, color = Color.DarkGray)
        }
    }
}


fun getStatusText(status: Int): String {
    return when (status) {
        0 -> "Chờ xác nhận"
        1 -> "Đơn hàng đã hủy"
        2 -> "Đơn hàng đã được xác nhận"
        3 -> "Đang giao hàng"
        4 -> "Giao hàng thành công"
        else -> "Người dùng hủy đơn hàng"
    }
}