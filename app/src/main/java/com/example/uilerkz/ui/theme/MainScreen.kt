package com.example.uilerkz

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uilerkz.ui.theme.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Menu, Screen.Like, Screen.Chat, Screen.Profile)
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
            composable(Screen.Registration.route) { RegistrationScreen(navController) }
            composable(Screen.Menu.route) { SearchScreen(navController) }
            composable(Screen.Details.route) { DetailsScreen(navController) }
            composable(Screen.Like.route) { LikedScreen() }
            composable(Screen.Chat.route) { ChatScreen() }
            composable(Screen.Profile.route) { if (authViewModel.checkAuthStatus()) ProfileScreen(navController) else LoginScreen(navController) }
        }
    }
}



@Composable
fun LikedScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
fun ChatScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}
