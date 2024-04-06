package com.example.p3project.sources.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.p3project.sources.presentation.screens.Screen
import com.example.p3project.sources.presentation.screens.addtask.AddTaskScreen
import com.example.p3project.sources.presentation.screens.calendar.CalendarScreen
import com.example.p3project.sources.presentation.screens.overview.OverviewScreen
import com.example.p3project.sources.presentation.screens.taskview.TaskScreen
import com.example.p3project.sources.presentation.theme.P3ProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.OverviewScreen.route,
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

                        composable(
                            route = Screen.AddtaskScreen.route + "?{taskId}",
                            arguments = listOf(navArgument("taskId") {nullable=true})
                        ) {backStackEntry ->
                            AddTaskScreen(navController, backStackEntry.arguments?.getInt("taskId"))
                        }
                    }
                }
            }
        }
    }
}