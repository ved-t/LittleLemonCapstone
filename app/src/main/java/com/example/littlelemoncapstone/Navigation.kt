package com.example.littlelemoncapstone

import android.content.Context
import android.os.Debug
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    val startDestination = if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
        Destinations.Home.route
    }
    else{
        Destinations.Onboarding.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(Destinations.Onboarding.route) { Onboarding(innerPadding, navController) }
        composable(Destinations.Home.route) { Home(innerPadding, navController) }
        composable(Destinations.Profile.route) { Profile(innerPadding, navController) }
    }

}