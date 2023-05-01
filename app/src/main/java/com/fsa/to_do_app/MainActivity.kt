package com.fsa.to_do_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
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
import com.fsa.to_do_app.presentation.content.create_task.composables.CreateTaskScreen
import com.fsa.to_do_app.presentation.content.dashboard.composables.DashboardScreen
import com.fsa.to_do_app.presentation.content.edit_category_list.composables.EditCategoryListScreen
import com.fsa.to_do_app.presentation.content.edit_task.EditTaskScreen
import com.fsa.to_do_app.presentation.content.splash_screen.SplashScreen
import com.fsa.to_do_app.presentation.theme.ToDoAppTheme
import com.fsa.to_do_app.util.CHANNEL_ID
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locale = Locale("az")
        Locale.setDefault(locale)
        val config: Configuration = baseContext.resources.configuration
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

        createNotificationChannel(this)
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    NavHost(navController, startDestination = NavDestinations.SPLASH_SCREEN) {
                        composable(route = NavDestinations.SPLASH_SCREEN) {
                            SplashScreen(navigateToMain = {
                                navController.popBackStack()
                                navController.navigate(NavDestinations.DASHBOARD_SCREEN)
                            })
                        }
                        composable(route = NavDestinations.DASHBOARD_SCREEN) {
                            DashboardScreen(navigateToCreateTask = {
                                navController.navigate(
                                    NavDestinations.CREATE_ACTION_SCREEN
                                )
                            },
                                navigateToEditTask = { navController.navigate("${NavDestinations.EDIT_ACTION_SCREEN}/$it") },
                                navigateToCreateCategory = { navController.navigate(NavDestinations.EDIT_CATEGORY_LIST_SCREEN) })
                        }

                        composable(route = NavDestinations.CREATE_ACTION_SCREEN) {
                            CreateTaskScreen(navigateBack = { navController.popBackStack() })
                        }
                        composable(route = NavDestinations.EDIT_CATEGORY_LIST_SCREEN) {
                            EditCategoryListScreen(navigateBack = { navController.popBackStack() })
                        }
                        composable(
                            route = "${NavDestinations.EDIT_ACTION_SCREEN}/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            EditTaskScreen(navigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TASK REMINDER"
            val descriptionText = "Notification sent for reminding about a task"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}

