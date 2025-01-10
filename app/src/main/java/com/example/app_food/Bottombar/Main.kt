package com.example.app_food.Bottombar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
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
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_food.Screen.Home
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel
import com.example.app_food.ViewModel.ShopcartViewModel
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun MainScreen(navController: NavController,proViewModel: ProViewModel,protypeViewModel: ProtypeViewModel,oderViewModel: OderViewModel,userViewModel: UserViewModel,email : String,shopcartViewModel: ShopcartViewModel) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home,0),
        NavItem("Shopcart", Icons.Default.ShoppingCart,0),
        NavItem("Oder", Icons.Default.Shop,2),
        NavItem("Setting", Icons.Default.Settings,0),
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
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIdex, navController = navController,proViewModel,protypeViewModel,shopcartViewModel,email,oderViewModel,userViewModel)
    }
}

@SuppressLint("NewApi")
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController,
    proViewModel: ProViewModel,
    protypeViewModel: ProtypeViewModel,
    shopcartViewModel: ShopcartViewModel,
    email: String,
    viewModel: OderViewModel,
    userViewModel: UserViewModel
) {
    when (selectedIndex) {
        0 -> Home(
            navController = navController,
            proViewModel = proViewModel,
            protypeViewModel = protypeViewModel,
            email = email,
            modifier = modifier
        )
        2 -> OderScreen(
            viewModel = viewModel,
            email = email,
            proViewModel = proViewModel,
            modifier = modifier
        )
        1 -> Shopcart(
            navController = navController,
            email = email,
            modifier = modifier,
            viewModel = shopcartViewModel,
            proViewModel = proViewModel,
            oderviewmodel = viewModel
        )
        3 -> Setting(
            email = email,
            navController = navController,
            userViewModel = userViewModel,
            modifier = modifier
        )
    }
}



