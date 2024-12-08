package com.example.app_food.BottombarAdmin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_food.R
import com.example.app_food.Screen.ProductDetail
import com.example.app_food.ScreenAdmin.Home
import com.example.app_food.ScreenAdmin.New
import com.example.app_food.ScreenAdmin.Product

import com.example.app_food.ScreenAdmin.Productype
import com.example.app_food.ScreenAdmin.Turnover
import com.example.app_food.ScreenAdmin.User
import com.example.app_food.ScreenAdmin.productDetailAdmin
import com.example.app_food.Toptab.Main
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel
import com.example.app_food.ViewModel.UserViewModel
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnNavDrawer(userViewModel: UserViewModel=UserViewModel(),ProViewModel: ProViewModel=ProViewModel(),ProtypeViewModel: ProtypeViewModel=ProtypeViewModel(),OderViewModel: OderViewModel= OderViewModel()) {
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val height= LocalConfiguration.current.screenHeightDp.dp
    val weight= LocalConfiguration.current.screenWidthDp.dp
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF673AB7))
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                            contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Image(painter = painterResource(R.drawable.user), contentDescription = "", modifier = Modifier.height(height*0.15f).width(weight*0.3f))
                        Text(text = "Admin", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }
                }
                NavigationDrawerItem(label = { Text(text = "Home" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.home), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Home.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "ProductType" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.drink), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Turnover.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "Turnover" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.grow), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Turnover.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "Product" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.fastfood), contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Product.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "News" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.file), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.New.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "Oder" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.checkout), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Oder.screen){
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "User" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.userr), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.User.screen){
                            popUpTo(0)
                        }
                    })
            }
        }
    ) {
        Scaffold(topBar = {
            val coroutineScope= rememberCoroutineScope()
            TopAppBar(title = { Text(text = "App food") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF673AB7),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            Icons.Rounded.Menu, contentDescription = "MenuButton"
                        )
                    }
                },)
        }) {paddingValues ->
        NavHost(navController =  navigationController, startDestination = Screens.Home.screen, modifier = Modifier.padding(paddingValues)){
                composable(Screens.Home.screen){ Home() }
                composable(Screens.ProductType.screen){ Productype()  }
            composable(Screens.Product.screen) {
                Product(onButtonClick = { productId ->
                    navigationController.navigate("productDetail/$productId")}, productViewModel = ProViewModel, protypeViewModel = ProtypeViewModel)
            }
                composable(Screens.User.screen) { User(userViewModel)  }
                composable(Screens.Oder.screen) { Main(OderViewModel,ProViewModel)  }
                composable(Screens.New.screen) { New()  }
            composable(Screens.Turnover.screen) { Turnover()  }
            composable(
                "productDetail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                productDetailAdmin(productId,navigationController, protypeViewModel = ProtypeViewModel, viewModel = ProViewModel)
            }
            }
        }
    }
}