package com.example.uilerkz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LikedScreen(navController: NavHostController) {
    val allAdvertisements = mutableListOf(
        R.drawable.advertise1,
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
    )

    // Состояние для хранения избранных
    val items = remember { mutableStateOf<List<String>>(emptyList()) }

    val authViewModel = AuthViewModel()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Box {
            Text(
                text = "Favorites",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF231D4F),
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Spacer(Modifier.height(20.dp))
        if (!authViewModel.checkAuthStatus()) {
            Text(
                "You need to login",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        } else {
            val auth = FirebaseAuth.getInstance()
            val firestore = FirebaseFirestore.getInstance()
            val currentUser = auth.currentUser

            // Загружаем избранные элементы из Firestore асинхронно
            LaunchedEffect(currentUser?.uid) {
                if (currentUser != null) {
                    val userId = currentUser.uid
                    val docRef = firestore.collection("favorites").document(userId)

                    docRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val itemsList = documentSnapshot.get("items") as? List<String> ?: emptyList()
                                items.value = itemsList // обновляем состояние
                            }
                        }
                        .addOnFailureListener {
                            items.value = emptyList() // если ошибка, оставляем пустой список
                        }
                }
            }
            for (adv in allAdvertisements) {
                if (items.value.contains(adv.toString())) {

                    Column(modifier = Modifier
                        .background(
                            color = Color(0xFFF7F7F5), shape = RoundedCornerShape(size = 30.dp)
                        )
                        .clickable {
                            val selectedImage = R.drawable.advertise1
                            val selectedAddress = "Dostyk, 85"

                            navController.navigate("details/${selectedImage}/${selectedAddress}") {
                                popUpTo("details") { inclusive = true }
                            }

                        }
                    ) {
                        Image(
                            painter = painterResource(id = adv),
                            contentDescription = "",
                            Modifier.clip(RoundedCornerShape(30.dp))
                        )
                        Spacer(Modifier.height(25.dp))
                    }
                    Spacer(Modifier.height(30.dp))
                }
            }
        }
    }
}
