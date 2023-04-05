package com.fsa.to_do_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fsa.to_do_app.presentation.common.navigation.NavDestinations
import com.fsa.to_do_app.presentation.content.create_action.composables.CreateActionScreen
import com.fsa.to_do_app.presentation.content.dashboard.composables.DashboardScreen
import com.fsa.to_do_app.presentation.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = NavDestinations.DASHBOARD_SCREEN) {
                        composable(route = NavDestinations.DASHBOARD_SCREEN) {
                            DashboardScreen { navController.navigate(NavDestinations.CREATE_ACTION_SCREEN) }
                        }

                        composable(route = NavDestinations.CREATE_ACTION_SCREEN) {
                            CreateActionScreen(navigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
