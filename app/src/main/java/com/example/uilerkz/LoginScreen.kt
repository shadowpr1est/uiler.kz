package com.example.uilerkz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Uiler.kz",
                style = TextStyle(
                    fontSize = 30.sp,
                    lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "easy to live",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(293.dp)
                .background(color = Color(0xFFE7E7E5), shape = RoundedCornerShape(size = 52.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                var mail by remember { mutableStateOf("") }
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),

                    )
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = mail,
                    onValueChange = {
                        mail = it
                    },

                    modifier = Modifier.height(20.dp)
                )
            }
            Spacer(Modifier.height(30.dp))
            Column {
                var password by remember { mutableStateOf("") }
                Text(
                    text = "Password",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),

                    )
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.height(20.dp),
                )
            }
            Spacer(Modifier.height(50.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF888888),
                    contentColor = Color(0xFF000000),
                    disabledContainerColor = Color(0xFFE7E7E5),
                    disabledContentColor = Color(0xFFE7E7E5)
                ),
                modifier = Modifier
                    .width(148.dp)
                    .height(33.dp)
            ) {
                Text(
                    text = "Log in",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Spacer(Modifier.height(30.dp))
            Column(
                modifier = Modifier.clickable { },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(Modifier.height(7.dp))
                Box(
                    Modifier
                        .padding(0.dp)
                        .width(106.04244.dp)
                        .height(1.dp)
                        .background(color = Color(0xFF000000))
                )
            }
        }
    }
}