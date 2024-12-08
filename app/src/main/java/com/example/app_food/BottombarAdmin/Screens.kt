package com.example.app_food.BottombarAdmin

sealed class Screens(val screen : String) {
    data object Home: Screens("home")
    data object ProductType : Screens("ProductType")
    data object Product : Screens("Product")
    data object Oder: Screens ("Oder")
    data object User: Screens("User")
    data object New : Screens("New")
    data object Turnover : Screens("turnover")
}