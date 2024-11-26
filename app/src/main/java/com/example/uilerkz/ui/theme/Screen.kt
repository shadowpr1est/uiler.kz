package com.example.uilerkz.ui.theme

import androidx.compose.ui.text.font.FontWeight
import com.example.uilerkz.R
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uilerkz.ui.theme.UilerkzTheme

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Login : Screen("login", "Login", R.drawable.home_logo)
    object Registration : Screen("registration", "Register", R.drawable.shield)
    object Profile : Screen("profile", "Profile", R.drawable.user_profile)
}

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Uiler.kz", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold))
            Text(text = "easy to live", style = TextStyle(fontSize = 14.sp))
        }

        Spacer(Modifier.height(70.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(293.dp)
                .background(Color(0xFFE7E7E5)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var mail by remember { mutableStateOf("") }
            Text("Email", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal))
            Spacer(Modifier.height(10.dp))
            TextField(
                value = mail,
                onValueChange = { mail = it },
                modifier = Modifier.height(56.dp)
            )

            Spacer(Modifier.height(30.dp))

            var password by remember { mutableStateOf("") }
            Text("Password", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal))
            Spacer(Modifier.height(10.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.height(56.dp)
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = { },
                modifier = Modifier.width(148.dp).height(33.dp)
            ) {
                Text("Log in", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal))
            }
        }

        Spacer(Modifier.height(30.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Register", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal))
            Spacer(Modifier.height(7.dp))
            Box(
                Modifier
                    .padding(0.dp)
                    .width(106.dp)
                    .height(1.dp)
                    .background(Color.Black)
            )
        }
    }
}

@Composable
fun RegistrationScreen() {
    // Similar layout to the LoginScreen
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold))
        Spacer(Modifier.height(20.dp))
        Text("This is the profile screen.", style = TextStyle(fontSize = 18.sp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    UilerkzTheme {
        LoginScreen()
    }
}
