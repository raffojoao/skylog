package com.example.skylog.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skylog.model.LocalData
import com.example.skylog.screens.HomeScreen
import com.example.skylog.screens.LoginScreen
import com.example.skylog.screens.RegisterScreen


class Navigation {

    private lateinit var navController: NavHostController

    @Composable
    fun Create() {
        navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(Routes.EventList.route) {
                CallScaffold(navController).CreateScreen(Routes.EventList.route)
            }
            composable(
                route = "eventCreate?eventId={eventId}",
                arguments = listOf(
                    navArgument("eventId") {
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")
                CallScaffold(navController).CreateScreen(
                    screen = Routes.EventCreate.route,
                    eventId = eventId

                )
            }
            composable(Routes.Home.route) {
                CallScaffold(navController).CreateScreen(
                    screen = Routes.Home.route
                )
            }
            composable(Routes.Register.route) {
                CallScaffold( navController).CreateScreen(
                    screen = Routes.Register.route
                )
            }
            composable(Routes.Login.route) {
                CallScaffold( navController).CreateScreen(screen = Routes.Login.route)
            }
            composable(Routes.Timeline.route) {
                CallScaffold( navController).CreateScreen(screen = Routes.Timeline.route)
            }
            composable(
                route = "eventDetails?eventId={eventId}",
                arguments = listOf(
                    navArgument("eventId") {
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")!!
                CallScaffold(navController).CreateScreen(
                    screen = Routes.EventDetails.route,
                    eventId = eventId
                )
            }

        }
    }
}