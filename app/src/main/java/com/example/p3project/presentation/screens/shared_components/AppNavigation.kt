package com.example.p3project.presentation.screens.shared_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
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
            icon = { Icon(Icons.Filled.Home, "") },
            selected = (current_selected == Screen.OverviewScreen),
            onClick = { handleNavigation(navController, Screen.OverviewScreen) },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, "") },
            selected = (current_selected == Screen.CalendarScreen),
            onClick = { handleNavigation(navController, Screen.CalendarScreen) },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ThumbUp, "") },
            selected = (current_selected == Screen.ViewTaskScreen),
            onClick = { handleNavigation(navController, Screen.ViewTaskScreen) },
        )
    }
}