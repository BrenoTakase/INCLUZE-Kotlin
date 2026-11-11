package com.aether.incluze.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aether.incluze.ui.report.ReportViewModel
import com.aether.incluze.ui.screens.HomeScreen
import com.aether.incluze.ui.screens.ListScreen
import com.aether.incluze.ui.screens.FormScreen

@Composable
fun AppNavigation(navController: NavHostController, viewModel: ReportViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToList = { navController.navigate("list") },
                onNavigateToForm = { navController.navigate("form") }
            )
        }
        composable("list") {
            ListScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToForm = { navController.navigate("form") }
            )
        }
        composable("form") {
            FormScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
