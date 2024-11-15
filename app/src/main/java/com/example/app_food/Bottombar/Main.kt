package com.example.app_food.Bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
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
<<<<<<< HEAD

@Composable
fun MainScreen() {
=======
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Notification", Icons.Default.Notifications),
        NavItem("Setting", Icons.Default.Settings)
    )
    val selectedIdex by remember {
        mutableIntStateOf(0)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed{index,navItem->
                    NavigationBarItem(
                        selected = selectedIdex==index,
                        onClick = {},
                        icon= {
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
<<<<<<< HEAD
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIdex)
=======
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIdex, navController = navController)
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    }


}

@Composable
<<<<<<< HEAD
fun ContentScreen(modifier: Modifier=Modifier,selectedIndex:Int){
    when(selectedIndex){
        0-> Home(onItemproclick = {})
=======
fun ContentScreen(modifier: Modifier=Modifier,selectedIndex:Int,navController: NavController){
    when(selectedIndex){
        0-> Home(navController)
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    }
}


