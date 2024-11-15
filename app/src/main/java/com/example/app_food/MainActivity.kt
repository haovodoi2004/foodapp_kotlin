package com.example.app_food

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
import com.example.app_food.Bottombar.Home
import com.example.app_food.Repository.Repository
import com.example.app_food.Screen.Sigin
import com.example.app_food.Screen.Sigup
import com.example.app_food.Bottombar.MainScreen
import com.example.app_food.Bottombar.ProductList
import com.example.app_food.Screen.ProductDetail
import com.example.app_food.ViewModel.UserViewModel
import com.example.app_food.ViewModel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    private val userViewModel:UserViewModel by viewModels {
        UserViewModelFactory(Repository())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppNavigation(){
        val navController= rememberNavController()
        val context= LocalContext.current
        NavHost(navController=navController, startDestination = "home") {
            composable("signin"){
                Sigin(userViewModel = userViewModel,onSignupClick={
                    navController.navigate("signup")
                },navController)
            }
            composable("signup"){
               Sigup(navController,userViewModel = userViewModel)
            }
            composable("home"){
                Home(navController)
            }

            composable("main"){
                MainScreen(navController)
            }
            composable(
                route = "productDetail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                if (productId != null) {
                    ProductDetail(navController,productId)
                } else {
                    Toast.makeText(context,"product id lỗi cmnr",Toast.LENGTH_SHORT).show()
                    // Xử lý trường hợp null (ví dụ hiển thị thông báo lỗi hoặc điều hướng về màn hình khác)
                }

            }
//            composable("home"){
//                MainScreen(navController,userViewModel = userViewModel)
//            }
        }
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

            }}
