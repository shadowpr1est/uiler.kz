package com.example.uilerkz

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.uilerkz.ui.theme.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun HistoryScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    // Using remember to store booking data
    val bookings = remember { mutableStateOf<List<Map<String, String>>>(emptyList()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.caretleft),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.navigate(Screen.Profile.route)
                    }
            )
            Text(
                text = "Bookings",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF231D4F),
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (currentUser != null) {
            val userRef = firestore.collection("users").document(currentUser.uid)

            // Fetch bookings from Firestore
            userRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val bookingsList = documentSnapshot.get("bookings") as? List<Map<String, String>> ?: emptyList()
                        bookings.value = bookingsList
                    } else {
                        Log.e("pricing", "User document does not exist.")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("pricing", "Error fetching user data: ${e.message}")
                }
        }

        DisplayBookings(bookings.value, onRemoveBooking = { bookingToRemove ->
            // Remove the selected booking from Firestore and update local state
            if (currentUser != null) {
                val userRef = firestore.collection("users").document(currentUser.uid)

                val updatedBookings = bookings.value.filterNot { it == bookingToRemove }

                userRef.update("bookings", updatedBookings)
                    .addOnSuccessListener {
                        bookings.value = updatedBookings
                    }
                    .addOnFailureListener { e ->
                        Log.e("pricing", "Error removing booking: ${e.message}")
                    }
            }
        })
    }
}

@Composable
fun DisplayBookings(bookings: List<Map<String, String>>, onRemoveBooking: (Map<String, String>) -> Unit) {
    if (bookings.isEmpty()) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "You haven't booked anything yet",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF231D4F),
                ),
            )
        }
    } else {
        Spacer(Modifier.height(20.dp))

        LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(bookings) { booking ->
                val startDate = booking["start_date"]
                val endDate = booking["end_date"]
                val imageRes = booking["image"]?.toInt() ?: R.drawable.advertise1 // Provide a default image if not found
                val address = booking["address"] ?: "Samal 2, 85"

                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp)
                ) {
                    Column(
                        Modifier.fillMaxSize().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Booking Image",
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(Modifier.height(8.dp))
                        Text(text = "Start Date: $startDate")
                        Text(text = "End Date: $endDate")
                        Text(text = "Address: $address")

                        Spacer(Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFF1565C0), shape = RoundedCornerShape(30.dp))
                                .padding(horizontal = 14.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Remove Booking",
                                color = Color.White,
                                modifier = Modifier
                                    .clickable { onRemoveBooking(booking) }
                            )
                        }
                    }
                }
            }
        }
    }
}
