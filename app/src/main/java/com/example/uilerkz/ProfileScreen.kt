package com.example.uilerkz

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    val authViewModel = AuthViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = "Profile",
            style = TextStyle(
                fontSize = 30.sp,
                lineHeight = 22.sp,
//                fontFamily = FontFamily(Font(R.font.space_grotesk)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .width(373.dp)
                .height(78.dp)
                .background(color = Color(0xFFE7E7E5), shape = RoundedCornerShape(size = 52.dp))
                .padding(start = 25.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.user_profile),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Text(
                text = authViewModel.getUserEmail().toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(15.dp))
        ProfileButtons(R.drawable.shield, "Security",navController)
        ProfileButtons(R.drawable.wallet, "Payments",navController)
        ProfileButtons(R.drawable.bell, "Notifications",navController)
        ProfileButtons(R.drawable.settings, "Log out", navController,"login")
    }
}

@Composable
fun ProfileButtons(icon: Int, text: String,navController: NavHostController,path: String="") {
    val authViewModel = AuthViewModel()
    Column(modifier = Modifier.padding(horizontal = 26.dp).clickable {
        if (path == "login"){
            authViewModel.signOut()
            navController.navigate(path)
        }
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(start = 12.dp)

            )
        }
        Box(
            Modifier
                .padding(0.dp)
                .width(328.dp)
                .height(1.dp)
                .background(color = Color(0xFF000000))
        )
        Spacer(Modifier.height(18.dp))
    }
}
