package com.app.quranapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.quranapp.model.Surah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

@HiltViewModel
class QuranViewModel @Inject constructor(
    @ApplicationContext private val context: Context): ViewModel() {
    private val _surahList = MutableStateFlow<List<Surah>>(emptyList())
    val surahList: StateFlow<List<Surah>> = _surahList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading



   init {
       loadQuranJson(context)
   }
    fun loadQuranJson(context: Context) {


        viewModelScope.launch {
             _isLoading.value = true
            val data = withContext(Dispatchers.IO) {
                val input = context.assets.open("quran_by_surah.json")
                val reader = InputStreamReader(input)
                val type = object : TypeToken<List<Surah>>() {}.type
                Gson().fromJson<List<Surah>>(reader, type)
            }
            _surahList.value = data
            _isLoading.value = false
        }
   }
    fun getSurahByIndex(index: Int): Surah? = _surahList.value.find { it.number == index }

}
