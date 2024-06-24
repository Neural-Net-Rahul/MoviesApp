package com.example.retrofit4_paging.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.navigation.Routes
import com.example.retrofit4_paging.ui.theme.fontFamily
import com.example.retrofit4_paging.utils.SharedPreferencesManager
import androidx.compose.material3.Text

@Composable
fun MovieListScreen(movie : ResultM , navController : NavHostController , context : Context) {
    val rating = movie.vote_average
    val roundedRating = "%.1f".format(rating)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                SharedPreferencesManager.saveSelectedMovie(
                    context = context,
                    movie = movie
                )
                navController.navigate(Routes.MovieDetail.routes)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(130.dp)
                    .padding(vertical = 8.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title ?: "",
                    color = Color.White,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = roundedRating ,
                        color = Color.Gray,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Release Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = " ${movie.release_date ?: "-"}",
                        color = Color.Gray,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Language",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = " ${movie.original_language ?: "-"}",
                        color = Color.Gray,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Adult Content",
                        tint = if (movie.adult == true) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (movie.adult == true) " For Adults Only" else " For All Age Groups",
                        color = if (movie.adult == true) Color.Red else Color.Green,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}
