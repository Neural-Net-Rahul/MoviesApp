package com.example.retrofit4_paging.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retrofit4_paging.utils.FavouritesManager
import androidx.compose.material3.Text
import com.example.retrofit4_paging.ui.theme.family2

@Composable
fun FavoritesScreen(navController: NavHostController, modifier: Modifier = Modifier.background(Color(0xFF242A32))) {
    val context = LocalContext.current
    val favoriteMovies = FavouritesManager.getFavorites(context).reversed()
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Favorites",
                color = Color.White,
                fontFamily = family2,
                fontSize = 25.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(favoriteMovies) { movie ->
                MovieListScreen(movie = movie, navController = navController, context)
                Spacer(Modifier.height(5.dp))
                DividerLine()
                Spacer(Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .width(64.dp)
            .background(Color.White)
            .padding(vertical = 12.dp)
    )
}

