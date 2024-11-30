package com.example.uilerkz

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OnboardingPager() {
    val pagerState = rememberPagerState()
    val images = listOf(
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DotsIndicator(
            totalDots = images.size,
            selectedIndex = pagerState.currentPage,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(8.dp)
                    .background(color, RoundedCornerShape(50))
            )
        }
    }
}
@Composable
fun BookingSection(onBook: (String, String, String, Int) -> Unit, image: Int, address: String) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    var arrivalDate by remember { mutableStateOf("") }
    var leavingDate by remember { mutableStateOf("") }
    var isSelectingArrival by remember { mutableStateOf(false) }
    var isSelectingLeaving by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { isSelectingArrival = true }) {
                Text(text = if (arrivalDate.isEmpty()) "Choose Arrival Date" else arrivalDate)
            }

            Button(onClick = { isSelectingLeaving = true }) {
                Text(text = if (leavingDate.isEmpty()) "Choose Leaving Date" else leavingDate)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onBook(arrivalDate, leavingDate, address, image)
                if (currentUser != null) {
                    val newBooking = mapOf(
                        "start_date" to arrivalDate,
                        "end_date" to leavingDate,
                        "address" to address,
                        "image" to image.toString() // Store the image ID or URL
                    )

                    // Get the reference to the user's document
                    val userDocRef = firestore.collection("users").document(currentUser.uid)

                    // Check if the user already has bookings
                    userDocRef.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                // If bookings already exist, add the new booking to the existing array
                                val existingBookings = document.get("bookings") as? List<Map<String, Any>> ?: emptyList()
                                val updatedBookings = existingBookings + newBooking

                                // Update the bookings array
                                userDocRef.update("bookings", updatedBookings)
                                    .addOnSuccessListener {
                                        Log.d("pricing", "Booking added to existing bookings")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("pricing", "Error adding new booking: ${e.message}")
                                    }
                            } else {
                                // If no bookings exist, create a new bookings field with the new booking
                                userDocRef.set(
                                    mapOf("bookings" to listOf(newBooking))
                                )
                                    .addOnSuccessListener {
                                        Log.d("pricing", "Successfully created bookings field and stored data")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("pricing", "Error creating bookings field: ${e.message}")
                                    }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("pricing", "Error checking for existing bookings: ${e.message}")
                        }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Book")
        }
    }

    if (isSelectingArrival) {
        CalendarDialog(
            onDateSelected = { selectedDate ->
                arrivalDate = selectedDate
                isSelectingArrival = false
            },
            onDismiss = { isSelectingArrival = false }
        )
    }

    if (isSelectingLeaving) {
        CalendarDialog(
            onDateSelected = { selectedDate ->
                leavingDate = selectedDate
                isSelectingLeaving = false
            },
            onDismiss = { isSelectingLeaving = false }
        )
    }
}


@Composable
fun CalendarDialog(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Date") },
        text = {
            CustomCalendarWithMonthSelection { selectedDate ->
                onDateSelected(selectedDate)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun CustomCalendarWithMonthSelection(onDateSelected: (String) -> Unit) {
    var currentMonthIndex by remember { mutableStateOf(10) } // November (index starts at 0)
    var currentYear by remember { mutableStateOf(2024) }

    // Months list
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    // Days in month calculation (handles leap years)
    val daysInMonth = remember(currentMonthIndex, currentYear) {
        when (currentMonthIndex) {
            0, 2, 4, 6, 7, 9, 11 -> 31 // Jan, Mar, May, Jul, Aug, Oct, Dec
            3, 5, 8, 10 -> 30 // Apr, Jun, Sep, Nov
            1 -> if (isLeapYear(currentYear)) 29 else 28 // Feb
            else -> 30
        }
    }
    val days = (1..daysInMonth).toList()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Month and Year Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (currentMonthIndex == 0) {
                    currentMonthIndex = 11
                    currentYear--
                } else {
                    currentMonthIndex--
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.caretleft), // Replace with your back arrow
                    contentDescription = "Previous Month"
                )
            }

            Text(
                "${months[currentMonthIndex]} $currentYear",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )

            IconButton(onClick = {
                if (currentMonthIndex == 11) {
                    currentMonthIndex = 0
                    currentYear++
                } else {
                    currentMonthIndex++
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.caretleft), // Replace with your forward arrow
                    contentDescription = "Next Month",
                    modifier = Modifier
                        .graphicsLayer(rotationZ = 180f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Grid
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            // Divide the days into rows of 7
            items(days.chunked(7)) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    week.forEach { day ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp)
                                .background(Color.LightGray, RoundedCornerShape(50))
                                .clickable {
                                    val selectedDate =
                                        "$currentYear-${(currentMonthIndex + 1).toString().padStart(2, '0')}-${
                                            day.toString().padStart(2, '0')
                                        }"
                                    onDateSelected(selectedDate)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                style = TextStyle(color = Color.Black)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper function to determine leap year
fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}


@Composable
fun DetailsScreen(
    navController: NavHostController,
    image: Int = R.drawable.advertise1,
    address: String = "Dostyk, 85",
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isLiked by remember { mutableStateOf(false) }

    LaunchedEffect(image, address) {
        fetchFavorites { favorites ->
            isLiked = favorites.any { it["image"] == image.toString() && it["address"] == address }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Back Button (aligned at the top left)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.caretleft),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(24.dp).align(Alignment.CenterStart)
                )

                Text(
                    text = "Uiler.kz",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Onboarding Pager (Image carousel with dots)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Constrain the height of OnboardingPager
            ) {
                OnboardingPager()
            }

            Spacer(Modifier.height(16.dp))

            // Address and Heart Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = address,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                IconToggleButton(
                    checked = isLiked,
                    onCheckedChange = { checked ->
                        toggleFavorite(image.toString(), address) { success, message ->
                            if (success) {
                                isLiked = checked
                            }
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message ?: "Unknown Error")
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(if (isLiked) R.drawable.heart_filled else R.drawable.heart),
                        contentDescription = if (isLiked) "Remove from Favorites" else "Add to Favorites",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            BookingSection(
                onBook = { arrival, leaving, bookedAddress, bookedImage ->
                    // Handle the booking, for example show a snackbar
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Booking Successful: $arrival to $leaving at $bookedAddress"
                        )
                    }
                },
                image = image,
                address = address
            )

            Spacer(Modifier.height(20.dp))

            // Additional Information Section (e.g., description)
            Text(
                text = "This is a cozy apartment located in the heart of the city. Enjoy flexible subscription-based rentals for your convenience.",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
fun toggleFavorite(image: String, address: String, onComplete: (Boolean, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    if (currentUser != null) {
        val userDocRef = firestore.collection("users").document(currentUser.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                val newFavorite = mapOf("image" to image, "address" to address)
                val existingFavorites = document.get("favorites") as? List<Map<String, String>> ?: emptyList()

                // Check if the item is already in the favorites
                if (existingFavorites.any { it["image"] == image && it["address"] == address }) {
                    // Remove item from favorites
                    val updatedFavorites = existingFavorites.filterNot { it["image"] == image && it["address"] == address }
                    userDocRef.update("favorites", updatedFavorites)
                        .addOnSuccessListener {
                            onComplete(false, "Removed from favorites")
                            // After updating, you may want to reload the favorites data
                            fetchFavorites { favorites ->
                                // Handle the updated favorites list
                                // Update UI state based on the new favorites
                                // Example: Update `isLiked` state here
                            }
                        }
                        .addOnFailureListener { e ->
                            onComplete(false, "Error removing from favorites: ${e.message}")
                        }
                } else {
                    // Add item to favorites
                    val updatedFavorites = existingFavorites + newFavorite
                    userDocRef.update("favorites", updatedFavorites)
                        .addOnSuccessListener {
                            onComplete(true, "Added to favorites")
                            // After updating, you may want to reload the favorites data
                            fetchFavorites { favorites ->
                                // Handle the updated favorites list
                                // Update UI state based on the new favorites
                                // Example: Update `isLiked` state here
                            }
                        }
                        .addOnFailureListener { e ->
                            onComplete(false, "Error adding to favorites: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                onComplete(false, "Error fetching user data: ${e.message}")
            }
    } else {
        onComplete(false, "No user logged in")
    }
}


fun fetchFavorites(onComplete: (List<Map<String, String>>) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    if (currentUser != null) {
        val userRef = firestore.collection("users").document(currentUser.uid)

        // Fetch favorites from Firestore
        userRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val favoritesList = documentSnapshot.get("favorites") as? List<Map<String, String>> ?: emptyList()
                    onComplete(favoritesList)
                } else {
                    Log.e("pricing", "User document does not exist.")
                    onComplete(emptyList())
                }
            }
            .addOnFailureListener { e ->
                Log.e("pricing", "Error fetching user data: ${e.message}")
                onComplete(emptyList())
            }
    } else {
        onComplete(emptyList()) // If no user is logged in, return empty list
    }
}
