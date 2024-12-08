package com.example.app_food.ScreenAdmin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.app_food.ApiServer.Apiserver
import com.example.app_food.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("NewApi")
@Composable
fun Turnover() {
    var dateStart by remember { mutableStateOf(LocalDate.now()) }
    var dateEnd by remember { mutableStateOf(LocalDate.now()) }
    var totalRevenue by remember { mutableStateOf(0) }
    val context = LocalContext.current



    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Thống kê doanh thu")

            // Ngày bắt đầu
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
                    readOnly = true // Ngăn không cho chỉnh sửa trực tiếp
                )
            }

            // Ngày kết thúc
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
                    readOnly = true // Ngăn không cho chỉnh sửa trực tiếp
                )
            }

            // Nút tính doanh thu
            Button(onClick = {
                if (dateStart > dateEnd) {
                    totalRevenue = 0
                } else {
                    RetrofitInstance.api.getRevenue(dateStart.toString(), dateEnd.toString())
                        .enqueue(object : Callback<Apiserver.RevenueResponse> {
                            override fun onResponse(call: Call<Apiserver.RevenueResponse>, response: Response<Apiserver.RevenueResponse>) {
                                if (response.isSuccessful) {
                                    totalRevenue = response.body()?.totalRevenue ?: 0
                                } else {
                                    totalRevenue = 0
                                }
                            }

                            override fun onFailure(call: Call<Apiserver.RevenueResponse>, t: Throwable) {
                                totalRevenue = 0 // Xử lý lỗi
                            }
                        })
                }
            }) {
                Text(text = "Ok")
            }

            // Hiển thị tổng doanh thu
            Text(text = "Tổng doanh thu: $totalRevenue")
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

// Hàm tính doanh thu
@SuppressLint("NewApi")
fun calculateRevenue(revenues: List<Revenue>, startDate: LocalDate, endDate: LocalDate): Int {
    return revenues.filter { it.date in startDate..endDate }
        .sumOf { it.amount }
}

// Dữ liệu doanh thu
data class Revenue(val date: LocalDate, val amount: Int)
