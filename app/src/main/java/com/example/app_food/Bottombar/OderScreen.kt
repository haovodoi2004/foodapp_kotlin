package com.example.app_food.Bottombar

import android.annotation.SuppressLint
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_food.Model.Oder
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun OderScreen(navController: NavController,viewModel: OderViewModel,email : String,proViewModel: ProViewModel,modifier: Modifier){
    val listoder by viewModel.oder.observeAsState(initial = emptyList())

        LaunchedEffect(listoder) {
            if(listoder.isEmpty()) {
            viewModel.fetchoder()
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Danh sách đơn hàng", textAlign = TextAlign.Center, fontSize = 30.sp)
            if (listoder.isEmpty()) {
                Text("đang load dữ liệu")
            } else {
                LazyColumn {
                    items(listoder, key = { it.id}) { item ->
                        if (item.emailuser == email) {
                            oderItem(item, proViewModel,navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun oderItem(order: Oder, proViewModel: ProViewModel,navController: NavController) {
    val product = proViewModel.products.value[order.id_pro]

    // Fetch product details if not available
    LaunchedEffect(order.id_pro) {
        if (product == null) {
            proViewModel.gettProById(order.id_pro)
        }
    }

    Card(
        onClick = {
            navController.navigate("oderdetail/${order.id}/${order.emailuser}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            product?.let { pr ->
                // Product image
                AsyncImage(
                    model = pr.avatar,
                    contentDescription = pr.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)) // Rounded corners for image
                        .background(Color.Gray.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Order details
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Tên : ${order.name}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sản phẩm: ${pr.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Giá: $${order.all}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Thời gian: ${order.date}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(
//                        text = "Customer: ${order.}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
                }
            }
        }
    }
}
