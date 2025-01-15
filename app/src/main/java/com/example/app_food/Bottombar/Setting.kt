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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
fun Setting(
    email: String,
    navController: NavController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val user by userViewModel.userr.observeAsState()

    LaunchedEffect(email) {
        userViewModel.getuser(email)
    }

    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // User Info Card
        Card(
            onClick = {
                navController.navigate("userDetail/$email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            user?.let { us ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 16.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = us.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = us.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.go),
                        contentDescription = "Navigate Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Logout Button
        Button(
            onClick = {
                navController.navigate("signin")
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(0.6f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Đăng xuất",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
