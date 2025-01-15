package com.example.app_food

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_food.Screen.Home
import com.example.app_food.Screen.Sigin
import com.example.app_food.Screen.Sigup
import com.example.app_food.Bottombar.MainScreen
import com.example.app_food.Bottombar.Setting
import com.example.app_food.Bottombar.userDetail
import com.example.app_food.BottombarAdmin.LearnNavDrawer
import com.example.app_food.Screen.ProductDetail
import com.example.app_food.ViewModel.OderViewModel
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel
import com.example.app_food.ViewModel.ShopcartViewModel
import com.example.app_food.ViewModel.UserViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }}

    @SuppressLint("NewApi")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppNavigation(proViewModel: ProViewModel=ProViewModel(),protypeViewModel: ProtypeViewModel=ProtypeViewModel(),oderViewModel: OderViewModel=OderViewModel(),userViewModel: UserViewModel=UserViewModel(),shopcartViewModel: ShopcartViewModel=ShopcartViewModel()){
        val navController= rememberNavController()
        val context= LocalContext.current
        NavHost(navController=navController, startDestination = "signin") {
            composable("signin"){
                Sigin(onSignupClick={
                    navController.navigate("signup")
                },navController)
            }
            composable("signup"){
               Sigup(navController)
            }
            composable("home/{email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                Home(navController, proViewModel, protypeViewModel, email!!, modifier = Modifier)
            }

            composable("mainadmin"){
                LearnNavDrawer(onClick = {
                    navController.navigate("signin")
                })
            }

            composable("main/{email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType })){
                    backStackEntry ->
                val userId = backStackEntry.arguments?.getString("email")
                if (userId != null) {
                    MainScreen(navController,proViewModel,protypeViewModel,oderViewModel,userViewModel,userId,shopcartViewModel)
                } else {
                    Toast.makeText(context,"product id lỗi cmnr",Toast.LENGTH_SHORT).show()
                    // Xử lý trường hợp null (ví dụ hiển thị thông báo lỗi hoặc điều hướng về màn hình khác)
                }

            }
            composable(
                route = "userDetail/{email}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val email = backStackEntry.arguments?.getString("email")
                if ( email != null) {
                    userDetail(onClick={
                        navController.navigate("")
                    },
                        email,navController,userViewModel)
                } else {
                    Toast.makeText(context,"product id lỗi cmnr",Toast.LENGTH_SHORT).show()
                    // Xử lý trường hợp null (ví dụ hiển thị thông báo lỗi hoặc điều hướng về màn hình khác)
                }

            }
            composable(
                route = "productDetail/{productId}/{email}",
                arguments = listOf(
                    navArgument("productId") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val email = backStackEntry.arguments?.getString("email")
                if (productId != null&& email != null) {
                    ProductDetail(navController,productId,email,proViewModel,shopcartViewModel,oderViewModel)
                } else {
                    Toast.makeText(context,"product id lỗi cmnr",Toast.LENGTH_SHORT).show()
                    // Xử lý trường hợp null (ví dụ hiển thị thông báo lỗi hoặc điều hướng về màn hình khác)
                }

            }


//            composable(
//                route = "setting",
//                arguments = listOf(
//                    navArgument("email") {
//                        type = NavType.StringType
//                        nullable = false
//                    }
//                )
//            ) { backStackEntry ->
//                val email = backStackEntry.arguments?.getString("email")
//                if (email != null) {
//                    Setting(
//                        email = email,
//                        navController = navController,
//                        userViewModel = userViewModel,
//                        modifier = Modifier
//                    )
//                } else {
//                    // Xử lý lỗi nếu email bị null
//                }
//            }



        }

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Forgot Password", fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Please sign in to your existing account", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle sending code */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(text = "SEND CODE", color = Color.White)
        }
    }

            }}}
