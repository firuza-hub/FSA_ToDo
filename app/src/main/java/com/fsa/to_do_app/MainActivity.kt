package com.fsa.to_do_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fsa.to_do_app.presentation.common.navigation.NavDestinations
import com.fsa.to_do_app.presentation.content.create_action.composables.CreateTaskScreen
import com.fsa.to_do_app.presentation.content.dashboard.composables.DashboardScreen
import com.fsa.to_do_app.presentation.content.edit_action.EditActionScreen
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
                            DashboardScreen(navigateToCreateTask = {
                                navController.navigate(
                                    NavDestinations.CREATE_ACTION_SCREEN
                                )
                            },
                                navigateToEditAction = { navController.navigate("${NavDestinations.EDIT_ACTION_SCREEN}/$it") })
                        }

                        composable(route = NavDestinations.CREATE_ACTION_SCREEN) {
                            CreateTaskScreen(navigateBack = { navController.popBackStack() })
                        }
                        composable(
                            route = "${NavDestinations.EDIT_ACTION_SCREEN}/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            EditActionScreen(navigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
