package com.example.app_food.ScreenAdmin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app_food.ApiServer.Apiserver
import com.example.app_food.Model.Oder
import com.example.app_food.Retrofit.RetrofitInstance
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("NewApi")
@Composable
fun Turnover(oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    var dateStart by remember { mutableStateOf(LocalDate.now()) }
    var dateEnd by remember { mutableStateOf(LocalDate.now()) }
    var totalRevenue by remember { mutableStateOf(0) }
    var orders by remember { mutableStateOf<List<Oder>>(emptyList()) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thống kê doanh thu",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ngày bắt đầu
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                        dateStart = LocalDate.of(year, month + 1, day)
                    }
                    showDatePicker(context, listener, dateStart)
                }) {
                    Icon(imageVector = Icons.Default.AccessAlarm, contentDescription = "Chọn ngày bắt đầu")
                }

                OutlinedTextField(
                    value = dateStart.toString(),
                    onValueChange = {},
                    label = { Text(text = "Chọn ngày bắt đầu") },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            // Ngày kết thúc
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                        dateEnd = LocalDate.of(year, month + 1, day)
                    }
                    showDatePicker(context, listener, dateEnd)
                }) {
                    Icon(imageVector = Icons.Default.AccessAlarm, contentDescription = "Chọn ngày kết thúc")
                }

                OutlinedTextField(
                    value = dateEnd.toString(),
                    onValueChange = {},
                    label = { Text(text = "Chọn ngày kết thúc") },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            // Nút tính doanh thu
            Button(
                onClick = {
                    if (dateStart > dateEnd) {
                        totalRevenue = 0
                        orders = emptyList()
                    } else {
                        RetrofitInstance.api.getRevenue(dateStart.toString(), dateEnd.toString())
                            .enqueue(object : Callback<Apiserver.RevenueResponse> {
                                override fun onResponse(call: Call<Apiserver.RevenueResponse>, response: Response<Apiserver.RevenueResponse>) {
                                    if (response.isSuccessful) {
                                        orders = response.body()?.orders?.filter { it.status == 4 } ?: emptyList()
                                        totalRevenue = orders.sumOf { it.all }
                                    } else {
                                        totalRevenue = 0
                                        orders = emptyList()
                                    }
                                }

                                override fun onFailure(call: Call<Apiserver.RevenueResponse>, t: Throwable) {
                                    totalRevenue = 0
                                    orders = emptyList()
                                }
                            })
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Tính doanh thu")
            }

            // Hiển thị tổng doanh thu
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(10.dp)
            ) {
                Text(
                    text = "Tổng doanh thu: $totalRevenue",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Hiển thị danh sách đơn hàng
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orders, key = { it.id }) { item ->
                    OderItem(item, oderViewModel, proViewModel)
                }
            }
        }
    }
}

@Composable
fun OderItem(oder: Oder, oderViewModel: OderViewModel, proViewModel: ProViewModel) {
    val pro = proViewModel.products.value[oder.id_pro]
    LaunchedEffect(oder.id_pro) {
        if (pro == null) {
            proViewModel.gettProById(oder.id_pro)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pro?.avatar,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Tên đơn hàng: ${oder.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ngày: ${oder.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                pro?.let {
                    Text(
                        text = "Tên sản phẩm: ${it.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Loại: ${it.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}



// Hàm hiển thị DatePicker
@SuppressLint("NewApi")
fun showDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener, date: LocalDate) {
    java.util.Locale.setDefault(java.util.Locale.ENGLISH)
    val datePickerDialog = DatePickerDialog(
        context,
        listener,
        date.year,
        date.monthValue - 1,
        date.dayOfMonth
    )
    datePickerDialog.show()
}




