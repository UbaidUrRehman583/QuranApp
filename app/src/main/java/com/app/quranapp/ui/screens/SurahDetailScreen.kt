package com.app.quranapp.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.quranapp.model.Surah
import com.app.quranapp.ui.item.SurahItem
import com.app.quranapp.ui.theme.QuranAppTheme
import com.app.quranapp.ui.viewmodel.QuranViewModel


@Composable
fun SurahDetailScreenNavigation(
    index: Int,
    onBack: () -> Unit,
    viewModel: QuranViewModel,
) {
    val list = viewModel.getSurahByIndex(index)


    BackHandler {
        onBack()
    }
    SurahDetailScreen(list)
}
@Composable
fun ZoomableScrollableContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 4f)
                    offsetX += pan.x
                    offsetY += pan.y
                }
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offsetX
                translationY = offsetY
            }
            .fillMaxSize()
    ) {
        content()
    }
}



@Composable
fun SurahDetailScreen(
    list: Surah?,
 ) {
    val surah = list
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
     ) {

        ZoomableScrollableContent {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                surah?.ayahs?.let { ayahs ->
                    items(ayahs.size) { i ->
                        val aya = ayahs[i]
                        SurahItem(aya.ayah,aya.text)
                    }
                }
            }
        }

    }

}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview
@Composable
fun SurahDetailScreenPreview() {
    QuranAppTheme {
        SurahDetailScreen(
            list = Surah(),
         )
    }
}