package com.example.retrofit4_paging.navigation

sealed class Routes(val routes:String) {
    object Home : Routes("home")
    object Search : Routes("search")
    object Favourite : Routes("favourite")
    object BottomNav : Routes("bottomNav")
    object MovieDetail : Routes("movieDetail")
}