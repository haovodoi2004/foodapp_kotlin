package com.example.app_food.Bottombar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_food.R
import com.example.app_food.ViewModel.UserViewModel

@Composable
fun Setting(email : String , navController: NavController,userViewModel: UserViewModel,modifier: Modifier=Modifier){
    val user by userViewModel.userr.observeAsState()
    LaunchedEffect(email) {
        userViewModel.getuser(email)
    }
    Box (modifier = modifier.fillMaxSize()){
        Card(onClick = {
            navController.navigate("userDetail/${email}")
        }, modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight(0.15f).align(Alignment.TopCenter)) {
            user?.let { us->
                Row(modifier = Modifier.fillMaxHeight().fillMaxWidth(),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = us.name, fontSize = 20.sp)
                    Image(
                        
                        painter = painterResource(R.drawable.user),
                        contentDescription = "",
                        modifier = modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(painter = painterResource(R.drawable.go), contentDescription = "", modifier = Modifier.size(60.dp).padding(end = 20.dp))
                }
            }
        }

        Button(onClick = {
            navController.navigate("signin")
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)), modifier = Modifier.align(alignment = Alignment.BottomCenter) ) {
            Text(text = "Đăng xuất")
        }
    }
}