package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.p3project.presentation.screens.Screen


private fun handleNavigation(navController: NavController, screen: Screen) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun AppNavigation(navController: NavController, current_selected: Screen){
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Menu, "") },
            selected = (current_selected == Screen.StatScreen),
            onClick = { handleNavigation(navController, Screen.StatScreen) },
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, "") },
            selected = (current_selected == Screen.OverviewScreen),
            onClick = { handleNavigation(navController, Screen.OverviewScreen) },
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, "") },
            selected = (current_selected == Screen.CalendarScreen),
            onClick = { handleNavigation(navController, Screen.CalendarScreen) },
        )
    }
}