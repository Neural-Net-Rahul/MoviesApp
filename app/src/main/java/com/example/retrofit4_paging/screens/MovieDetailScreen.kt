package com.example.retrofit4_paging.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.ui.theme.fontFamily
import com.example.retrofit4_paging.utils.SharedPreferencesManager
import com.example.retrofit4_paging.utils.FavouritesManager
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(navController: NavHostController) {
    val context = LocalContext.current
    val selectedMovie = remember { SharedPreferencesManager.getSelectedMovie(context) }

    var isFavorite by remember { mutableStateOf(selectedMovie?.let { FavouritesManager.isFavorite(context, it) } ?: false) }
    var selectedTab by remember { mutableStateOf("Summary") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Define custom colors for the Snackbar
    val snackbarBackgroundColor = Color(0xFFFFFFFF)
    val snackbarContentColor = Color.Blue

    // Function to toggle favorite status
    fun toggleFavorite(movie: ResultM) {
        isFavorite = !isFavorite
        if (isFavorite) {
            FavouritesManager.addFavorite(context, movie)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "${movie.title} added to favorites",
                )
            }
        } else {
            FavouritesManager.removeFavorite(context, movie)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "${movie.title} removed from favorites",
                )
            }
        }
    }

    selectedMovie?.let { movie ->
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = snackbarBackgroundColor,
                        contentColor = snackbarContentColor
                    )
                }
            }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .size(25.dp)
                    )
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Magenta else Color.White,
                        modifier = Modifier
                            .clickable { toggleFavorite(movie) }
                            .size(50.dp)
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                            contentDescription = movie.title,
                            modifier = Modifier
                                .size(150.dp)
                                .offset(y = 110.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.title ?: "",
                            fontFamily = fontFamily,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .offset(y = 110.dp)
                                .padding(start = 15.dp, end = 15.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))

                CategoryTabs(movie) { tab ->
                    selectedTab = tab
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Movie Details based on selected tab
                when (selectedTab) {
                    "Release Date" -> Text("${movie.release_date}", fontSize = 20.sp, color = Color.White)
                    "Rating" -> {
                        RatingBar(rating = movie.vote_average ?: 0.0)
                        Text(text = "Out of 10", color = Color.White)
                    }
                    "Summary" ->
                        Text(
                            text = "${movie.overview}",
                            color = Color.White,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(15.dp),
                            fontSize = 18.sp,
                        )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (movie.video == true) "Video is available" else "Video is not available",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryTabs(movie: ResultM, onTabSelected: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf("Summary") }
    val tabs = listOf("Release Date", "Rating", "Summary")

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        tabs.forEach { tab ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    selectedTab = tab
                    onTabSelected(tab)
                }
            ) {
                Text(
                    text = tab,
                    color = if (selectedTab == tab) Color.White else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                )
                if (selectedTab == tab) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(40.dp)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}
