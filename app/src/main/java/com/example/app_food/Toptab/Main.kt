package com.example.app_food.Toptab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.app_food.R
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource

@Composable
fun Main() {
    val tabItem = listOf(
        tab(
            title = "Chờ xác nhận",
            unselected = R.drawable.file,
            selected = R.drawable.file
        ),
        tab(
            title = "Đã xác nhận",
            unselected = R.drawable.order,
            selected = R.drawable.order
        ),
        tab(
            title = "Đang giao",
            unselected = R.drawable.delivery,
            selected = R.drawable.delivery
        ),
        tab(
            title = "Đã giao",
            unselected = R.drawable.fast,
            selected = R.drawable.fast
        ),
        tab(
            title = "Đã hủy",
            unselected = R.drawable.file1,
            selected = R.drawable.file1
        ),
        tab(
            title = "Giao hàng thất  bại",
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
                    text = {
                        Text(text = "${item.title}")
                    },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (index == selectedTabIndex) {
                                    item.selected
                                } else item.unselected
                            ),
                            contentDescription = item.title
                        )
                    })
            }
        }
    }
}


data class tab(
    val title: String,
    val unselected: Int,
    val selected: Int
)