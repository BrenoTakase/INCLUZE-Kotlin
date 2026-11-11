package com.aether.incluze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.aether.incluze.ui.navigation.AppNavigation
import com.aether.incluze.ui.report.ReportViewModel
import com.aether.incluze.ui.report.ReportViewModelFactory
import com.aether.incluze.ui.theme.IncluzeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IncluzeTheme {
                val app = application as IncluzeApplication
                val viewModel: ReportViewModel = viewModel(
                    factory = ReportViewModelFactory(app.repository)
                )
                val navController = rememberNavController()
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}
