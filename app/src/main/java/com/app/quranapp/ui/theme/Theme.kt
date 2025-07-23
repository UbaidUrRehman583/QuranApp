package com.app.quranapp.ui.theme

// CustomColors.kt (or within Theme.kt)

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*
val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    tertiary = Color(0xFFCF6679),
    onTertiary = Color.Black,
)

val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFFFFBFE),
    onSurface = Color.Black,
    tertiary = Color(0xFF018786),
    onTertiary = Color.White,
)
*/
val CustomDarkColorScheme = darkColorScheme(
    primary = Color.White,           // Text or accent
    onPrimary = Color.Black,         // On primary background
    secondary = Color.LightGray,
    onSecondary = Color.Black,
    background = Color.Black,        // Main background
    onBackground = Color.White,      // Main text
    surface = Color.Black,           // Surfaces/cards
    onSurface = Color.White,         // Text on surfaces
    tertiary = Color.White,
    onTertiary = Color.Black,
)
val CustomLightColorScheme = lightColorScheme(
    primary = Color.Black,           // Text or accent
    onPrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    background = Color.White,        // Main background
    onBackground = Color.Black,      // Main text
    surface = Color.White,           // Surfaces/cards
    onSurface = Color.Black,         // Text on surfaces
    tertiary = Color.Black,
    onTertiary = Color.White,
)






@Composable
fun QuranAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        CustomDarkColorScheme
    } else {
        CustomLightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
fun QuranAppTheme1(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    colorSchemeOverride: ColorScheme? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = colorSchemeOverride ?: when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CustomDarkColorScheme
        else -> CustomLightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}






