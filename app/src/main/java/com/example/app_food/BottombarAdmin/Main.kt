package com.example.app_food.BottombarAdmin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MainScreenAdmin(navController: NavController){
    val navItemList = listOf(
        NavItem("Home",Icons.Default.Home,0),
        NavItem("",Icons.Default.Home,0),
        NavItem("Doanh thu",Icons.Default.Home,0)

    )
}