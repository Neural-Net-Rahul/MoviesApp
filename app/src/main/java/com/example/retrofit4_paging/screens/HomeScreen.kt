package com.example.retrofit4_paging.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.viewModel.MovieViewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.retrofit4_paging.navigation.Routes
import com.example.retrofit4_paging.utils.SharedPreferencesManager

@Composable
fun HomeScreen(
    navController : NavController ,
    viewModel : MovieViewModel = hiltViewModel() ,
    nowPlayingMovies : LazyPagingItems<ResultM>
) {
    var selectedCategory by remember { mutableStateOf("now_playing") }
    val movies = viewModel.getMovies(selectedCategory).collectAsLazyPagingItems()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242A32))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "What do you want to watch?",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SearchBar(navController)

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.height(300.dp)
        ) {
            items(nowPlayingMovies.itemSnapshotList.items) { movie ->
                MovieItem(movie, navController, context)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        CategoryTabs(selectedCategory) { category ->
            selectedCategory = category
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(350.dp)
        ) {
            items(movies.itemSnapshotList.items) { movie ->
                MovieItemBelow(movie, navController, context)
            }
        }
    }
}

@Composable
fun SearchBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3A3F47) , shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp , vertical = 12.dp)
            .clickable { navController.navigate("search") }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Search",
                color = Color(0xFF67686D),
            )
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xFF67686D)
            )
        }
    }
}

@Composable
fun CategoryTabs(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val categories = listOf("now_playing", "upcoming", "top_rated", "popular")
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onCategorySelected(category) }
            ) {
                Text(
                    text = category.replace("_", " ").capitalize(),
                    color = if (isSelected) Color.White else Color.Gray,
                )
                if (isSelected) {
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieItem(movie : ResultM , navController : NavController , context : Context) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .padding(top = 5.dp , end = 10.dp)
                .width(170.dp)
                .clickable {
                /* Navigate to movie details */
                    SharedPreferencesManager.saveSelectedMovie(
                        context = context,
                        movie = movie
                    )
                    navController.navigate(Routes.MovieDetail.routes)
                }
        ) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(40.dp))
            ) {
                Image(
                    painter = rememberImagePainter(data = imageUrl) ,
                    contentDescription = movie.title ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) ,
                )
            }
            movie.title?.let {
                Text(
                    text = it ,
                    color = Color.White ,
                )
            }
        }
}

@Composable
fun MovieItemBelow(movie : ResultM , navController : NavController , context : Context) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

    Box(
        modifier = Modifier.padding(top = 10.dp).clip(RoundedCornerShape(50.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .width(120.dp)
                .clickable {
                /* Navigate to movie details */
                    SharedPreferencesManager.saveSelectedMovie(
                        context = context,
                        movie = movie
                    )
                    navController.navigate(Routes.MovieDetail.routes)
                }
        ) {
            Box() {
                Image(
                    painter = rememberImagePainter(data = imageUrl) ,
                    contentDescription = movie.title ,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}
