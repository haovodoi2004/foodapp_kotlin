package com.example.app_food.BottombarAdmin

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_food.Bottombar.OderDetail
import com.example.app_food.Bottombar.userDetail
import com.example.app_food.Model.User
import com.example.app_food.R
import com.example.app_food.Screen.Sigin
import com.example.app_food.ScreenAdmin.ChangePassword
import com.example.app_food.ScreenAdmin.New
import com.example.app_food.ScreenAdmin.Newdetail
import com.example.app_food.ScreenAdmin.Product

import com.example.app_food.ScreenAdmin.Productype
import com.example.app_food.ScreenAdmin.Setting
import com.example.app_food.ScreenAdmin.Turnover
import com.example.app_food.ScreenAdmin.productDetailAdmin
import com.example.app_food.Toptab.Main
import com.example.app_food.Toptab.Mainuser
import com.example.app_food.ViewModel.NewViewModel
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel
import com.example.app_food.ViewModel.UserViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnNavDrawer(userViewModel: UserViewModel=UserViewModel(),ProViewModel: ProViewModel=ProViewModel(),ProtypeViewModel: ProtypeViewModel=ProtypeViewModel(),OderViewModel: OderViewModel= OderViewModel(),newViewModel: NewViewModel=NewViewModel(),onClick : ()->Unit,email : String) {
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
                        Image(painter = painterResource(R.drawable.user), contentDescription = "", modifier = Modifier
                            .height(height * 0.15f)
                            .width(weight * 0.3f))
                        Text(text = "Admin", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }
                }
//                NavigationDrawerItem(label = { Text(text = "logout" , color = Color.Black) },
//                    selected = false,
//                    icon = { Icon(painter = painterResource(R.drawable.logout), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
//                    onClick = {
//                        coroutineScope.launch {
//                            drawerState.close()
//                            // Điều hướng về màn hình đăng nhập và xóa stack
//                           onClick()
//                        }
//                    })

                NavigationDrawerItem(label = { Text(text = "ProductType" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.drink), contentDescription = "", tint = Color.Black,modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.ProductType.screen){
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

                NavigationDrawerItem(label = { Text(text = "Setting" , color = Color.Black) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp))},
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Screens.Setting.screen){
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
        NavHost(navController =  navigationController, startDestination = Screens.Product.screen, modifier = Modifier.padding(paddingValues)){


                composable(Screens.ProductType.screen){ Productype(ProtypeViewModel)  }
            composable(Screens.Product.screen) {
                Product(onButtonClick = { productId ->
                    navigationController.navigate("productDetail/$productId")}, productViewModel = ProViewModel, protypeViewModel = ProtypeViewModel)
            }
                composable(Screens.User.screen) { Mainuser(userViewModel)  }
                composable(Screens.Oder.screen) { Main(navigationController,OderViewModel,ProViewModel,email)  }
            composable(
                Screens.Setting.screen
            ) {
                Setting(userViewModel, email =email, navController = navigationController,onClick) }
                composable(Screens.New.screen) { New(onClick = {
                    newId->
                    navigationController.navigate("newDetail/$newId")},newViewModel, navController = navigationController)  }
            composable(Screens.Turnover.screen) { Turnover(OderViewModel,ProViewModel)  }

            composable(
                "productDetail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                productDetailAdmin(productId,navigationController, protypeViewModel = ProtypeViewModel, viewModel = ProViewModel)
            }

            composable("oderdetail/{oderId}/{email}",
                arguments = listOf(
                    navArgument("oderId") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType }
                )
            ){
                    backStackEntry ->
                val oderId = backStackEntry.arguments?.getString("oderId")
                val email = backStackEntry.arguments?.getString("email")
                OderDetail(navigationController, oderViewModel = OderViewModel,ProViewModel,oderId!!,email!!)
            }

            composable(
                route = "userDetail/{email}",

            ) {
                    userDetail(onClick={
                        navigationController.navigate("")
                    },
                        email,navigationController,userViewModel)
            }

            composable(route = "changePassword/{email}") {

                ChangePassword(navigationController,userViewModel,email)
            }

            composable(
                "newDetail/{newId}",
                arguments = listOf(navArgument("newId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("newId") ?: ""
                Newdetail(productId,navigationController, viewModel = newViewModel)
            }
        }
        }
    }
}