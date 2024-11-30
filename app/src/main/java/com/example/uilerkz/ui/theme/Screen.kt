package com.example.uilerkz.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.uilerkz.LoginScreen
import com.example.uilerkz.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Login : Screen("login", "Login", R.drawable.home_logo)
    object Registration : Screen("registration", "Register", R.drawable.shield)
    object Profile : Screen("profile", "", R.drawable.user_profile_circle)
    object History : Screen("history", "", R.drawable.user_profile_circle)
    object Details : Screen("details/{image}/{address}", "Details", R.drawable.user_profile)
    object Pricing : Screen("pricing", "", R.drawable.pricing)
    object Like : Screen("like", "", R.drawable.like)
    object Menu : Screen("menu", "", R.drawable.menu)
}