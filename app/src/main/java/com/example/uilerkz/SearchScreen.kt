package com.example.uilerkz

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen(navController = rememberNavController())
}

// Sample advertisement images for different categories
val l1 = listOf(
    R.drawable.l1a1,
    R.drawable.l1a2,
    R.drawable.l1a3,
    R.drawable.l1a4,
    R.drawable.l1a5,
)
val l2 = listOf(
    R.drawable.l2a1,
    R.drawable.l2a2,
    R.drawable.l2a3,
    R.drawable.l2a4,
    R.drawable.l2a5,
)
val l3 = listOf(
    R.drawable.l3a1,
    R.drawable.l3a2,
    R.drawable.l3a3,
    R.drawable.l3a4,
    R.drawable.l3a5,
)
val l4 = listOf(
    R.drawable.l4a1,
    R.drawable.l4a2,
    R.drawable.l4a3,
    R.drawable.l4a4,
    R.drawable.l4a5,
)
val l5 = listOf(
    R.drawable.l5a1,
    R.drawable.l5a2,
    R.drawable.l5a3,
    R.drawable.l5a4,
    R.drawable.l5a5,
)
val l6 = listOf(
    R.drawable.l6a1,
    R.drawable.l6a2,
    R.drawable.l6a3,
    R.drawable.l6a4,
    R.drawable.l6a5,
)
val l7 = listOf(
    R.drawable.l7a1,
    R.drawable.l7a2,
    R.drawable.l7a3,
    R.drawable.l7a4,
    R.drawable.l7a5,
)
val premium = listOf(
    l6[0],l2[0],l7[0]
)
val basic = listOf(
    l1[0],l5[0]
)
val standard = listOf(
    l3[0],l4[0]
)
@Composable
fun SearchScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf("Basic") }  // Track selected category
    var selectedLocation by remember { mutableStateOf<String?>(null) }  // Track selected location (region)
    var advertisementList by remember { mutableStateOf<List<Int>>(premium) } // Default category "Premium"
    var showLocationSelector by remember { mutableStateOf(false) }  // Control visibility of the location selector

    // Update advertisement list based on selected category
    when (selectedCategory) {
        "Basic" -> advertisementList = basic
        "Standard" -> advertisementList = standard
        "Premium" -> advertisementList = premium
        "Location" -> {
            advertisementList = if (selectedLocation != null) {
                // Filter ads based on the selected city (location)
                listOf(l1[0]) // Here you should return actual ads based on selected location
            } else {
                l1 // Default to Kazakhstan-wide ads when no location is selected
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Uiler.kz",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(icon = R.drawable.star, label = "Basic", onClick = { selectedCategory = "Basic" })
            CategoryItem(icon = R.drawable.home_logo, label = "Standard", onClick = { selectedCategory = "Standard" })
            CategoryItem(icon = R.drawable.lock, label = "Premium", onClick = { selectedCategory = "Premium" })
            CategoryItem(
                icon = R.drawable.map,
                label = selectedLocation ?: "Location", // Show the selected location or default "Location"
                onClick = {
                    // Toggle location selection visibility
                    showLocationSelector = !showLocationSelector
                    if (showLocationSelector) {
                        selectedCategory = "Location"  // Switch to Location category
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Handle "Location" category separately to show location selection
        if (showLocationSelector) {
            LocationSelector(
                selectedLocation = selectedLocation,
                onLocationSelected = { location ->
                    selectedLocation = location
                    showLocationSelector = false // Hide the location selector after selection
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Dynamically display the advertisements based on selected category or location
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(advertisementList.size) { index ->
                Advertisement(
                    image = advertisementList[index],
                    address = "Address for ${selectedLocation ?: "Kazakhstan"} ${index + 1}",
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun LocationSelector(
    selectedLocation: String?,
    onLocationSelected: (String) -> Unit
) {
    val kazakhstanRegions = listOf(
        "Almaty", "Astana", "Shymkent", "Karaganda", "Aktobe", "Taraz", "Pavlodar", "Ust-Kamenogorsk", "Atyrau"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Select Your City", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(10.dp))

        // Show each region as clickable items
        kazakhstanRegions.forEach { region ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLocationSelected(region) }
                    .padding(8.dp)
            ) {
                Text(
                    text = region,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )
                Spacer(modifier = Modifier.weight(1f))
                if (selectedLocation == region) {
                    Text(
                        text = "Selected",
                        style = TextStyle(fontSize = 14.sp, color = Color.Green)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
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
    image: Int,
    address: String = "Dostyk, 85",
    navController: NavHostController,
) {
    Column(modifier = Modifier.fillMaxWidth()
        .background(
            color = Color(0xFFF7F7F5), shape = RoundedCornerShape(size = 30.dp)
        )
        .clickable {
            Log.d("images", image.toString())
            navController.navigate("details/${image}/${address}") {
                popUpTo("details") { inclusive = true }
            }
        }
    ) {
        AsyncImage(
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(30.dp))
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
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(Modifier.height(25.dp))
    }
}
