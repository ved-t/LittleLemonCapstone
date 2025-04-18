package com.example.littlelemoncapstone

sealed class Destinations(
    val route: String
){
    object Onboarding: Destinations("Onboarding")
    object Home: Destinations("Home")
    object Profile: Destinations("Profile")
}