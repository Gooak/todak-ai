package com.example.today_diary_ai.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.today_diary_ai.presentation.navigation.BottomMenus

@Composable
fun BottomNavigator(navController : NavController){
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        BottomMenus.entries.forEach {
            item ->
            NavigationBarItem(
                label = { Text(
                    text = item.label
                    , modifier = Modifier.padding(top = 0.dp, start =0.dp,end=0.dp, bottom = 2.dp)
                ) },
                selected = currentRoute == item.name,
                icon = {
                    Icon(
                        painterResource(
                            id = if (currentRoute == item.name) {
                                item.selectedIcon
                            } else {
                                item.icon
                            }
                        ), contentDescription = item.name
                        , modifier = Modifier.padding(top = 2.dp, start =0.dp,end=0.dp, bottom = 2.dp)
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                , colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}