package com.example.uilerkz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.uilerkz.ui.theme.Screen

@Composable
fun SearchScreen(navController: NavHostController) {
    Column(
        modifier = Modifier

            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(24.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search",
                    color = Color.Gray,
                )
            }
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings Icon",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(icon = R.drawable.star, label = "Trends")
            CategoryItem(icon = R.drawable.home_logo, label = "Apartments")
            CategoryItem(icon = R.drawable.lock, label = "Hostels")
            CategoryItem(icon = R.drawable.map, label = "Almaty")
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Advertisement(R.drawable.advertise1, navController = navController)
                Advertisement(R.drawable.advertise1, navController = navController)
                Advertisement(R.drawable.advertise1, navController = navController)
                Advertisement(R.drawable.advertise1, navController = navController)
            }
        }
    }
}

@Composable
fun CategoryItem(icon: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = label,
        )
    }
}

@Composable
fun Advertisement(
    icon: Int,
    address: String = "Dostyk, 85",
    price: Int = 250000,
    navController: NavHostController
) {
    Column(modifier = Modifier
        .background(
            color = Color(0xFFF7F7F5), shape = RoundedCornerShape(size = 30.dp)
        )
        .clickable {
            navController.navigate("details") {
                popUpTo(Screen.Details.route) { inclusive = true }

            }
        }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            Modifier.clip(RoundedCornerShape(30.dp))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = address, style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
//            fontFamily = FontFamily(Font(R.)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "Details",
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier
                    .background(
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(horizontal = 13.dp, vertical = 5.dp)
            )
            Text(
                text = "$price tg",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
//                fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(Modifier.height(25.dp))
    }
}