package com.example.p3project.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.p3project.presentation.screens.Screen
import com.example.p3project.presentation.screens.calendar.CalendarScreen
import com.example.p3project.presentation.screens.overview.OverviewScreen
import com.example.p3project.presentation.theme.P3ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P3ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = {
                        BottomAppBar {
                            NavigationBarItem(
                                selected = false,
                                onClick = { /*TODO*/ },
                                icon = { Icon(Icons.Filled.Home, "") }
                            )
                        }
                    }) {paddingValues ->
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.OverviewScreen.route,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(
                                route = Screen.OverviewScreen.route
                            ) {
                                OverviewScreen(navController)
                            }
                            composable(
                                route = Screen.CalendarScreen.route
                            ) {
                                CalendarScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}