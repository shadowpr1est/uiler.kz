package com.example.uilerkz

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
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun ProfileScreen() {
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
                text = "Galymbek Zharylkassynov",
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
        ProfileButtons(R.drawable.settings, "Settings")
        ProfileButtons(R.drawable.shield, "Security")
        ProfileButtons(R.drawable.wallet, "Payments")
        ProfileButtons(R.drawable.bell, "Notifications")
    }
}

@Composable
fun ProfileButtons(icon: Int, text: String) {
    Column(modifier = Modifier.padding(horizontal = 26.dp).clickable {  }) {
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
