package com.example.uilerkz

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TarifCard(
    text: String,
    price: String,
    description: String,
    current: Boolean = false,
    onPlanSelected: () -> Unit
) {
    val backgroundColor = if (current) Color(0xFF90CAF9) else Color(0xFFEDEDED)
    val monthColor = if (current) Color(0xFF455A64) else Color(0xFF848199)

    Column(
        Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 26.dp))
            .padding(start = 30.dp, top = 20.dp, end = 30.dp, bottom = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$price KZT",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF231D4F),
                )
            )
            Text(
                text = "/month",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(500),
                    color = monthColor,
                ),
                modifier = Modifier.offset(y = 10.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF231D4F),
            )
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = description,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF455A64),
            )
        )
        Spacer(Modifier.height(12.dp))
        Box(
            Modifier
                .width(152.dp)
                .height(34.dp)
                .background(color = Color(0xFF1565C0), shape = RoundedCornerShape(size = 24.dp))
                .clickable { onPlanSelected() }
        ) {
            Text(
                text = if (current) "Activated" else "Choose plan",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    Spacer(Modifier.height(25.dp))
}

@Composable
fun PricingScreen() {
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val authViewModel = AuthViewModel()

    val plans = listOf(
        Triple("Basic", "200,000", "Enjoy essential features to get started. Perfect for casual users who need the basics."),
        Triple("Standard", "500,000", "Unlock advanced features and greater flexibility. Ideal for regular users who want more value."),
        Triple("Premium", "700,000", "Access everything we offer. Designed for power users who want the ultimate experience.")
    )


    var selectedPlan by remember { mutableStateOf("None") }

    LaunchedEffect(Unit) {
        retrieveUserData(firestore, currentUser) { plan ->
            selectedPlan = plan
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            Text(
                text = "Pricing",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF231D4F),
                )
            )
            Spacer(Modifier.height(15.dp))


            plans.forEach { plan ->
                val (name, price, description) = plan

                val isCurrentPlan = selectedPlan == name

                TarifCard(
                    text = name,
                    price = price,
                    description = description,
                    current = isCurrentPlan,
                    onPlanSelected = {
                        if (!isCurrentPlan) {
                            selectedPlan = name
                            savePlanToFirestore(firestore, currentUser, name)
                        }
                    }
                )
            }
        }
    }
}

fun savePlanToFirestore(firestore: FirebaseFirestore, currentUser: FirebaseUser?, plan: String) {
    if (currentUser != null) {
        firestore.collection("users")
            .document(currentUser.uid)
            .update("sub_type", plan)
            .addOnSuccessListener {
                println("Plan updated successfully!")
            }
            .addOnFailureListener { e ->
                println("Error updating plan: ${e.message}")
            }
    }
}



fun retrieveUserData(
    firestore: FirebaseFirestore,
    currentUser: FirebaseUser?,
    onDataLoaded: (String) -> Unit
) {
    if (currentUser != null) {
        firestore.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val subType = documentSnapshot.getString("sub_type") ?: "No subscription"
                    onDataLoaded(subType)
                } else {
                    onDataLoaded("None")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error fetching user data: ${e.message}")
                onDataLoaded("None")
            }
    } else {
        onDataLoaded("None")
    }
}
