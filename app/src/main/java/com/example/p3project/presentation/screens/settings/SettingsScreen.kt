package com.example.p3project.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.p3project.presentation.screens.Screen
import com.example.p3project.presentation.screens.sharedComponents.AppNavigation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.p3project.presentation.screens.settings.components.CategoryDialog
import com.example.p3project.presentation.screens.settings.components.CategoryInfo
import com.example.p3project.presentation.screens.settings.components.CreateCategoryDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: SettingsViewmodel = hiltViewModel()
                   ) {

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.onEvent(SettingsEvent.ReloadCategories)
    }


    CreateCategoryDialog(state.creatingCategory,
        {categoryName, categoryColor ->
            viewModel.onEvent(SettingsEvent.CreateCategory(categoryName, categoryColor))
        },
        {viewModel.onEvent(SettingsEvent.CloseDialog)}
    )

    CategoryDialog(category = state.selectedCategory,
        onDelete = { viewModel.onEvent(SettingsEvent.DeleteCategory(state.selectedCategory!!)) },
        onDismiss = {viewModel.onEvent(SettingsEvent.CloseDialog)})


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text("Settings")
                },
            )
        },
    ) {paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            CategoryInfo(
                state.categories,
                onPickCategory = {category -> viewModel.onEvent(SettingsEvent.ViewCategory(category))},
                onAddMore = {viewModel.onEvent(SettingsEvent.OpenCreateCategoryDialog)}
            )

            HorizontalDivider()
        }
    }
}