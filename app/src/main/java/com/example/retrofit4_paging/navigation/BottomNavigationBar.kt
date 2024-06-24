package com.example.retrofit4_paging.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import com.example.retrofit4_paging.models.BottomNavItem
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.screens.FavoritesScreen
import com.example.retrofit4_paging.screens.HomeScreen
import com.example.retrofit4_paging.screens.SearchScreen
import com.example.retrofit4_paging.R

@Composable
fun BottomNav(navController : NavHostController , nowPlayingMovies : LazyPagingItems<ResultM>){
    val navController1 = rememberNavController()
    Scaffold(bottomBar = {MyBottomBar(navController1)},) {
            innerPadding ->
        NavHost(
            navController = navController1,
            startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Routes.Home.routes){
                HomeScreen(navController , nowPlayingMovies = nowPlayingMovies)
            }
            composable(Routes.Search.routes){
                SearchScreen(navController)
            }
            composable(Routes.Favourite.routes){
                FavoritesScreen(navController)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController1 : NavHostController){
    val backStackEntry = navController1.currentBackStackEntry;
    val list = listOf(
        BottomNavItem("Home", Routes.Home.routes, R.drawable.house) ,
        BottomNavItem("Search", Routes.Search.routes, R.drawable.search),
        BottomNavItem("Favourites", Routes.Favourite.routes, R.drawable.favourite),
    )
    var selectedItem by remember { mutableStateOf(list.first()) }
    backStackEntry?.let { entry ->
        val route = entry.destination.route
        selectedItem = list.firstOrNull { it.route == route } ?: list.first()
    }
        BottomAppBar(
            containerColor = Color(0xFF242A32)
        ) {
            list.forEach { item ->
                val selected = item == selectedItem
                Log.d("check" , "${backStackEntry?.destination?.route}")
                Log.d("check" , "${item.title} and $selected")
                val tint = if (selected) Color(0xFF0296E5) else Color(0xFF67686D)
                NavigationBarItem(
                    selected = selected ,
                    onClick = {
                        navController1.navigate(item.route) {
                            selectedItem = item
                            popUpTo(navController1.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    } ,
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally ,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon) ,
                                contentDescription = item.title ,
                                tint = tint ,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = item.title ,
                                color = tint ,
                                modifier = Modifier.padding(top = 1.dp)
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Transparent,
                        unselectedIconColor = Color.Transparent,
                        selectedTextColor = Color.Transparent,
                        unselectedTextColor = Color.Transparent,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
}
