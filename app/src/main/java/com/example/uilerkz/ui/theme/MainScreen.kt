package com.example.uilerkz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uilerkz.ui.theme.Screen
import com.example.uilerkz.ui.theme.UilerkzTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Login, Screen.Registration, Screen.Profile)
    BottomNavigation(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        backgroundColor = Color(0xFFE7E7E5)
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(screen.icon), contentDescription = screen.title,
                    modifier = Modifier.padding(top = 20.dp, bottom = 7.dp).size(24.dp))},
                label = { Text(screen.title,modifier = Modifier.padding(bottom = 10.dp)) },
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

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Login.route) { LoginScreen() }
            composable(Screen.Registration.route) { RegistrationScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    UilerkzTheme {
        MainScreen()
    }
}

