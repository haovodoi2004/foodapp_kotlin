package com.example.app_food.ScreenAdmin

sealed class Screens(val screen : String) {
    data object Home: Screens("home")
    data object Setting : Screens ("setting")
    data object Person : Screens ("person")
}