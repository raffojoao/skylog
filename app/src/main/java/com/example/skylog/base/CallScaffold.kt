package com.example.skylog.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.skylog.model.LocalData
import com.example.skylog.screens.CreateEventScreen
import com.example.skylog.screens.CreateEventViewModel
import com.example.skylog.screens.ListEventViewModel
import com.example.skylog.screens.ListEventsScreen

class CallScaffold(localData: LocalData, val navController: NavController, ) {

    val listTaskViewModel = ListEventViewModel(localData, navController)
    val createEventViewModel = CreateEventViewModel(localData, navController)
//    val editTaskViewModel = EditTaskViewModel(localData, navController)
//    val detailTaskViewModel = DetailTaskViewModel(localData)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateScreen(screen: String): PaddingValues {
        Scaffold(
            topBar = {
                when(screen) {
                    Routes.EventList.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "SkyLog")
                            }
                        )
                    }
                    Routes.EventCreate.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Novo evento")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        null)
                                }
                            },
                            modifier = Modifier.background(color = Color(0xFF0000FF))
                        )
                    }
//                    Routes.TaskEdit.route -> {
//                        CenterAlignedTopAppBar(
//                            title = {
//                                Text(text = "Alteração de Tarefa")
//                            },
//                            navigationIcon = {
//                                IconButton(onClick = {
//                                    navController.popBackStack()
//                                }) {
//                                    Icon(
//                                        Icons.AutoMirrored.Filled.ArrowBack,
//                                        null)
//                                }
//                            }
//                        )
//                    }
//                    Routes.TaskDetail.route -> {
//                        CenterAlignedTopAppBar(
//                            title = {
//                                Text(text = "Exibir dados da Tarefa")
//                            },
//                            navigationIcon = {
//                                IconButton(onClick = {
//                                    navController.popBackStack()
//                                }) {
//                                    Icon(
//                                        Icons.AutoMirrored.Filled.ArrowBack,
//                                        null)
//                                }
//                            }
//                        )
//                    }
                }
            }
        ) { paddingValues ->
            when(screen) {
                Routes.EventList.route -> {
                    ListEventsScreen(paddingValues, listTaskViewModel)
                }
                Routes.EventCreate.route -> {
                    CreateEventScreen(paddingValues, createEventViewModel)
                }
//                Routes.EventEdit.route -> {
//                    EditTaskScreen(paddingValues, editTaskViewModel)
//                }
//                Routes.EventDetail.route -> {
//                    DetailTaskScreen(paddingValues, detailTaskViewModel)
//                }
            }
        }
        return PaddingValues()
    }
}