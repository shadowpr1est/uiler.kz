package com.example.uilerkz

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
@Composable
fun LikedScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    // Состояние для хранения избранных объявлений
    val favoriteItems = remember { mutableStateOf<List<Pair<Int, String>>>(emptyList()) }

    // Асинхронная загрузка данных из Firestore
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            // Получаем данные о избранных объявлениях для текущего пользователя
            val userDocRef = firestore.collection("users").document(currentUser.uid)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val favoritesList = document.get("favorites") as? List<Map<String, String>> ?: emptyList()
                        // Преобразуем данные из Firestore в список пар "изображение и адрес"
                        favoriteItems.value = favoritesList.map {
                            Pair(it["image"]?.toIntOrNull() ?: R.drawable.advertise1, it["address"] ?: "Unknown")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("LikedScreen", "Error fetching favorite items: ${e.message}")
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Заголовок
        Text(
            text = "Favorites",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF231D4F)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(20.dp))

        if (currentUser == null) {
            // Пользователь не авторизован
            Text(
                text = "You need to login",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
        } else {
            // Пользователь авторизован
            if (favoriteItems.value.isEmpty()) {
                Text(
                    text = "No favorites added yet",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                )
            } else {
                // Вывод избранных объявлений
                favoriteItems.value.forEach { (image, address) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = Color(0xFFF7F7F5),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                navController.navigate("details/$image/$address")
                            }
                    ) {
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = address,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF231D4F)
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
