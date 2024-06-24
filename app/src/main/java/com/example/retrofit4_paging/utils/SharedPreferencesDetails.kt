package com.example.retrofit4_paging.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.retrofit4_paging.models.ResultM
import com.google.gson.Gson

object SharedPreferencesManager {
    private const val PREF_NAME = "MoviePrefs"
    private const val KEY_MOVIE = "selected_movie"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSelectedMovie(context: Context, movie: ResultM) {
        val editor = getSharedPreferences(context).edit()
        val gson = Gson()
        val json = gson.toJson(movie)
        editor.putString(KEY_MOVIE, json)
        editor.apply()
    }

    fun getSelectedMovie(context: Context): ResultM? {
        val gson = Gson()
        val json = getSharedPreferences(context).getString(KEY_MOVIE, null)
        return gson.fromJson(json, ResultM::class.java)
    }

    fun clearSelectedMovie(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(KEY_MOVIE)
        editor.apply()
    }
}
