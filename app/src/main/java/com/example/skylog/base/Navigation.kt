package com.example.skylog.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skylog.model.LocalData

class Navigation {

    private lateinit var navController: NavHostController
    private lateinit var localData: LocalData

    @Composable
    fun Create() {
        navController = rememberNavController()
        localData = LocalData(LocalContext.current)

        NavHost(navController = navController, startDestination = Routes.EventList.route) {
            composable(Routes.EventList.route) {
                CallScaffold(localData, navController).CreateScreen(Routes.EventList.route)
            }
            composable(Routes.EventCreate.route) {
                CallScaffold(localData, navController).CreateScreen(Routes.EventCreate.route)
            }
//            composable(Routes.TaskEdit.route) {
//                CallScaffold(localData, navController).CreateScreen(Routes.TaskEdit.route)
//            }
//            composable(Routes.TaskDetail.route) {
//                CallScaffold(localData, navController).CreateScreen(Routes.TaskDetail.route)
//            }
        }
    }
}