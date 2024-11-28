package com.example.uilerkz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(
    navController: NavHostController,
    image: Int = R.drawable.advertise1,
    price: Int = 250000,
    address: String = "Dostyk, 85"
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Spacer(Modifier.height(10.dp))
        Image(
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
                .padding(horizontal = 20.dp),
            painter = painterResource(R.drawable.caretleft),
            contentDescription = ""
        )
        Spacer(Modifier.height(30.dp))
        Image(
            painter = painterResource(image), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(350.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = address,
                style = TextStyle(
                    fontSize = 28.sp,
                    lineHeight = 22.sp,
//                fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Image(painter = painterResource(R.drawable.heart), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth().
                    fillMaxHeight(1f)
                .background(color = Color(0xFFE7E7E5), shape = RoundedCornerShape(50.dp))
                .padding(top = 40.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource(R.drawable.resource_package), contentDescription = null)
                    Text(
                        text = "Bazis - A",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "two-room apartment",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ), modifier = Modifier.padding(start = 5.dp)
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "for 3 persons",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ), modifier = Modifier.padding(start = 5.dp)
                )
            }
            Spacer(Modifier.height(70.dp))
            Column(modifier = Modifier.fillMaxSize().background(color = Color.White, shape = RoundedCornerShape(50.dp)).padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$price tg",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 22.sp,
//                            fontFamily = FontFamily(Font(R.font.space_grotesk)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Column(Modifier.padding(horizontal = 25.dp , vertical = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {  },
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Book",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 22.sp,
//                                fontFamily = FontFamily(Font(R.font.space_grotesk)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                        Box(Modifier
                            .padding(0.dp)
                            .width(67.dp)
                            .height(1.dp)
                            .background(color = Color(0xFF000000)))
                    }

                }
            }
        }
    }
}