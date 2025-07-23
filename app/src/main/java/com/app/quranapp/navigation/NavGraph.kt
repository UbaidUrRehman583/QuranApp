package com.app.quranapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.quranapp.ui.screens.HomeScreenNavigation
import com.app.quranapp.ui.screens.SurahDetailScreenNavigation
import com.app.quranapp.ui.viewmodel.QuranViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    innerPadding: PaddingValues,
    ) {
    val navController = rememberNavController()
    val viewModel: QuranViewModel = hiltViewModel()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val showBottomBar =
        currentDestination?.route in listOf(Routes.Home.route, Routes.SurahDetail.route)
    val showBackIcon = currentDestination?.route == Routes.SurahDetail.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentDestination?.route) {
                            Routes.Home.route -> "Home"
                            Routes.SurahDetail.route -> "Surah Detail"
                            else -> ""
                        }
                    )
                },
                navigationIcon = {
                    if (showBackIcon) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        /*bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }*/
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) {
                HomeScreenNavigation(
                    viewModel = viewModel,
                    onSurahClick = { navController.navigate(it) }
                )
            }
            composable(Routes.SurahDetail.route,
                arguments = listOf(navArgument("surahIndex"){type = NavType.IntType})
            ) { backStackEntry ->
                val index = backStackEntry.arguments?.getInt("surahIndex") ?: 0

                SurahDetailScreenNavigation(
                    viewModel = viewModel,
                    index = index,
                    onBack = { navController.popBackStack() }
                )
            }

        }
    }
}

/*
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.HOME } == true,
            onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.SETTINGS } == true,
            onClick = {
                navController.navigate(Routes.SETTINGS) {
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                }
            }
        )
    }
}*/
