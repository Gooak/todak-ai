package com.example.todak_ai.presentation.ui.screens

import StatisticsScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.todak_ai.presentation.navigation.BottomMenus
import com.example.todak_ai.presentation.ui.components.BottomNavigator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val bottomBarVisibleRoutes = BottomMenus.entries.map { it.name }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in bottomBarVisibleRoutes) {
                BottomNavigator(navController)
            }
        },
    ) { interPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomMenus.DIARY.name,
            modifier = Modifier
                .padding(interPadding)
                .background(Color.White)
                .fillMaxSize()
        ) {
            composable(BottomMenus.DIARY.name) { backStackEntry ->
                DiaryListScreen(navController)
            }
            composable("DiaryAddScreen") { backStackEntry ->
                DiaryAddScreen(navController)
            }
            composable(BottomMenus.STATISTICS.name) { backStackEntry ->
                StatisticsScreen()
            }
        }
    }
}
