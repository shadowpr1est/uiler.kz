package com.example.uilerkz

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(paddingValues: PaddingValues) {
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Favorites", Icons.Filled.Favorite),
        NavigationItem("Notifications", Icons.Filled.Notifications),
        NavigationItem("Profile", Icons.Filled.Person)
    )


    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color(0xFFE7E7E5), tonalElevation = 4.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent // No highlight background
                )
            )
        }
    }
}

data class NavigationItem(
    val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector
)

