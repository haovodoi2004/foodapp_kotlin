package com.example.app_food.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetail(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)){

        }

        Box(modifier = Modifier.weight(7f).fillMaxSize().background(Color.Black)){
            Box(modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)).background(Color.White).fillMaxSize()){

            }
        }
    }
}