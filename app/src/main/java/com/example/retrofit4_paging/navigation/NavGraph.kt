package com.example.retrofit4_paging.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.retrofit4_paging.screens.FavoritesScreen
import com.example.retrofit4_paging.screens.HomeScreen
import com.example.retrofit4_paging.screens.MovieDetailScreen
import com.example.retrofit4_paging.screens.SearchScreen
import com.example.retrofit4_paging.viewModel.MovieViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel : MovieViewModel = hiltViewModel()
    val nowPlayingMovies = viewModel.getMovies("now_playing").collectAsLazyPagingItems()
    NavHost(navController = navController, startDestination = Routes.BottomNav.routes) {
        composable(Routes.Home.routes) { HomeScreen(navController = navController,viewModel,nowPlayingMovies) }
        composable(Routes.Search.routes) { SearchScreen(navController = navController) }
        composable(Routes.Favourite.routes) { FavoritesScreen(navController = navController) }
        composable(Routes.MovieDetail.routes) {MovieDetailScreen(navController = navController)}
        composable(Routes.BottomNav.routes){
            BottomNav(navController = navController,nowPlayingMovies)
        }
    }
}