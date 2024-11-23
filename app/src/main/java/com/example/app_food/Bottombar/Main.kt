package com.example.app_food.Bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home,0),
        NavItem("Notification", Icons.Default.Notifications,0),
        NavItem("Setting", Icons.Default.Settings,2),
        NavItem("Shopcart", Icons.Default.ShoppingCart,0)
    )
    var selectedIdex by remember {
        mutableIntStateOf(0)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed{index,navItem->
                    NavigationBarItem(
                        selected = selectedIdex==index,
                        onClick = {
                            selectedIdex = index },
                        icon= {
                            BadgedBox(badge = {
                                Badge( modifier = Modifier.padding(4.dp) ){
                                    if (navItem.badgeCount.toInt() > 0) { // Chỉ hiển thị khi badgeCount > 0
                                        Badge {
                                            Text(text = navItem.badgeCount.toString())
                                        }
                                    }
                                }
                            }) { }
                            androidx.compose.material3.Icon(imageVector = navItem.icon, contentDescription = "")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIdex, navController = navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier=Modifier,selectedIndex:Int,navController: NavController){
    when(selectedIndex){
        0-> Home(navController)
        3-> Shopcart(navController)
    }
}


