package com.example.dewminiproject.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: Int) = "product_details/$productId"
    }
    object PlaceOrder : Screen("place_order/{productId}") {
        fun createRoute(productId: Int) = "place_order/$productId"
    }
    object OrderHistory : Screen("order_history")
    object Profile : Screen("profile")
    object Notifications : Screen("notifications")
    object Support : Screen("support")
}
