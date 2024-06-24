package com.example.retrofit4_paging.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.retrofit4_paging.models.ResultM
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavouritesManager{
    private const val PREFS_NAME = "movie_prefs"
    private const val KEY_FAVORITES = "favorites"

    private val gson = Gson()

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveFavorites(context: Context, favorites: List<ResultM>) {
        val json = gson.toJson(favorites)
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_FAVORITES, json)
            apply()
        }
    }

    fun getFavorites(context: Context): List<ResultM> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_FAVORITES, null) ?: return emptyList()
        val type = object : TypeToken<List<ResultM>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addFavorite(context: Context, movie: ResultM) {
        val favorites = getFavorites(context).toMutableList()
        favorites.add(movie)
        saveFavorites(context, favorites)
    }

    fun removeFavorite(context: Context, movie: ResultM) {
        val favorites = getFavorites(context).toMutableList()
        favorites.removeAll { it.id == movie.id }
        saveFavorites(context, favorites)
    }

    fun isFavorite(context: Context, movie: ResultM): Boolean {
        return getFavorites(context).any { it.id == movie.id }
    }
}
