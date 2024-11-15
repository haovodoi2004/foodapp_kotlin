package com.example.app_food.Welcom

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_food.MainActivity
import kotlinx.coroutines.delay

class Flash: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            SplashScr()
        }
    }
}
@Preview
@Composable
fun SplashScr(){
    val alpha= remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val context= LocalContext.current
    LaunchedEffect(key1 = true){
        alpha.animateTo(1f, animationSpec = tween(1500))
        delay(3000)
        context.startActivity(Intent(context,MainActivity::class.java))
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red),
        contentAlignment = Alignment.Center
    ){
        Image(modifier = Modifier.alpha(alpha.value).size(300.dp),
            painter = painterResource(id = com.example.app_food.R.drawable.bibimba), contentDescription = "màn hình chào")
    }
}