package com.app.quranapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {
    @Serializable
    data object Home : Routes("home_screen")

    @Serializable
    data object SurahDetail : Routes("surah_detail_screen/{surahIndex}"){
        fun createRoute(surahIndex: Int) = "surah_detail_screen/$surahIndex"

    }
}