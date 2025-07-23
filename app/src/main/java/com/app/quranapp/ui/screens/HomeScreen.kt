package com.app.quranapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.quranapp.model.Surah
import com.app.quranapp.navigation.Routes
import com.app.quranapp.ui.theme.QuranAppTheme
import com.app.quranapp.ui.viewmodel.QuranViewModel


@Composable
fun HomeScreenNavigation(
    onSurahClick: (String) -> Unit,
    viewModel: QuranViewModel ,
) {
    val loading by viewModel.isLoading.collectAsState()
    val list by viewModel.surahList.collectAsState()
    HomeScreen(
        loading,
        list,
        onSurahClick = { onSurahClick.invoke(it) }
    )
}

@Composable
fun HomeScreen(
    loading: Boolean,
    list: List<Surah>?,
    onSurahClick: (String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val surahs = remember { list }
            LazyColumn(Modifier.fillMaxSize()) {
                surahs?.let {
                    items(surahs.size) { index ->
                    val surah = surahs[index]
                    ListItem(
                        headlineContent = { Text(surah.name.toString()) },
                        modifier = Modifier.clickable {
                            onSurahClick(Routes.SurahDetail.createRoute(surah.number?:1))
                        }
                    )
                }
                }
            }
        }
    }
}


/*@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview*/

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreview() {
    QuranAppTheme {
        HomeScreen(
            loading = false,
            list = listOf(
                Surah(name = "Al-Fatiha", ayahs = listOf(), number = 1),
                Surah(name = "Al-Baqarah", ayahs = listOf(), number = 2)
            ), onSurahClick = {})
    }
}