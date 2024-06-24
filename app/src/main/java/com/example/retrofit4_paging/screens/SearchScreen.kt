package com.example.retrofit4_paging.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.retrofit4_paging.viewModel.MovieViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun SearchScreen(navController: NavHostController, modifier: Modifier = Modifier.background(Color(0xFF242A32))) {
    val viewModel: MovieViewModel = hiltViewModel()
    val context = LocalContext.current

    var searchText by rememberSaveable { mutableStateOf("") }

    val movies = viewModel.searchMovies(searchText).collectAsLazyPagingItems()

    val scrollState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(56.dp)
                .background(color = Color(0xFF3A3F47), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xFF67686D),
                modifier = Modifier.padding(start = 16.dp)
            )

            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 16.dp),
                placeholder = {
                    Text(
                        text = "Search movies",
                        color = Color(0xFF67686D),                    )
                },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(movies.itemSnapshotList.items) { movie ->
                MovieListScreen(movie, navController, context)
                Spacer(Modifier.height(5.dp))
                DividerLine()
                Spacer(Modifier.height(5.dp))
            }
        }
    }
}

