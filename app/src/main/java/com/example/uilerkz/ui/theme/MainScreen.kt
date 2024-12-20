package com.example.uilerkz

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uilerkz.ui.theme.Screen
import kotlin.math.log

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Menu, Screen.Like, Screen.Pricing, Screen.Profile)
    BottomNavigation(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        backgroundColor = Color(0xFFE7E7E5)
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon), contentDescription = screen.title,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 7.dp)
                            .size(24.dp)
                    )
                },
                label = { Text(screen.title, modifier = Modifier.padding(bottom = 10.dp)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Menu.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Registration.route) { RegistrationScreen(navController) }
            composable(Screen.Menu.route) { SearchScreen(navController) }
            composable(
                route = "details/{image}/{address}",
                arguments = listOf(
                    navArgument("image") { type = NavType.IntType },  // Make sure this is IntType
                    navArgument("address") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                // Extracting the arguments
                val image = backStackEntry.arguments?.getInt("image") ?: 0 // getInt() should be used here
                val address = backStackEntry.arguments?.getString("address") ?: "Default Address"
                Log.d("images", image.toString()) // Now this should print the correct value
                DetailsScreen(navController = navController, image = image, address = address)
            }
            composable(Screen.Like.route) { LikedScreen(navController) }
            composable(Screen.Pricing.route) { PricingScreen() }
            composable(Screen.Profile.route) { if (authViewModel.checkAuthStatus()) ProfileScreen(navController) else LoginScreen(navController) }
        }
    }
}



