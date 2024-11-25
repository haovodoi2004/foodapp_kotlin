package com.example.app_food.ScreenAdmin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Setting(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Home", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}