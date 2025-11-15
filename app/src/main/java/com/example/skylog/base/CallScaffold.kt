package com.example.skylog.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.skylog.components.BottomMenu
import com.example.skylog.model.LocalData
import com.example.skylog.screens.CreateEventScreen
import com.example.skylog.screens.CreateEventViewModel
import com.example.skylog.screens.EventDetailsScreen
import com.example.skylog.screens.EventDetailsViewModel
import com.example.skylog.screens.HomeScreen
import com.example.skylog.screens.ListEventViewModel
import com.example.skylog.screens.ListEventsScreen
import com.example.skylog.screens.LoginScreen
import com.example.skylog.screens.LoginViewModel
import com.example.skylog.screens.RegisterScreen
import com.example.skylog.screens.RegisterViewModel
import com.example.skylog.screens.TimelineScreen
import com.example.skylog.screens.TimelineViewModel
import com.google.firebase.auth.FirebaseAuth

class CallScaffold(val navController: NavController, ) {

    val listTaskViewModel = ListEventViewModel( navController)
    val createEventViewModel = CreateEventViewModel( navController)
    val loginViewModel = LoginViewModel(navController)
    val registerViewModel = RegisterViewModel(navController)
    val eventDetailsViewModel = EventDetailsViewModel()
    val timelineViewModel = TimelineViewModel(navController)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateScreen(screen: String, eventId: String? = null): PaddingValues {
        Scaffold(
            topBar = {
                when(screen) {
                    Routes.EventList.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Seus eventos")
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0B132B),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    }
                    Routes.EventCreate.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                val title: String = if (eventId != null) {
                                    "Editar Evento"
                                } else {
                                    "Novo evento"
                                }
                                Text(text = title)
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
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0B132B),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    }
                    Routes.Login.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Login")
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
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0B132B),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    }
                    Routes.Register.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Criar conta")
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
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0B132B),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    }
                    Routes.EventDetails.route -> {
                        CenterAlignedTopAppBar(
                            title = { Text(text = "Detalhes do Evento") },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        null
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF0B132B),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    }

                }
            },
            bottomBar = {
                if (screen == Routes.EventList.route || screen == Routes.Timeline.route) {
                    BottomMenu(
                        currentRoute = screen,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(Routes.EventList.route) { inclusive = false }
                            }
                        },
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate(Routes.Login.route) {
                                popUpTo(0)
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            when(screen) {
                Routes.Home.route -> {
                    HomeScreen(navController)
                }
                Routes.EventList.route -> {
                    ListEventsScreen(paddingValues, listTaskViewModel)
                }
                Routes.EventCreate.route -> {
                    CreateEventScreen(paddingValues, createEventViewModel, eventId)
                }
                Routes.Login.route -> {
                    LoginScreen(paddingValues, loginViewModel)
                }
                Routes.Register.route -> {
                    RegisterScreen(paddingValues, registerViewModel)
                }
                Routes.EventDetails.route -> {
                    EventDetailsScreen(
                        paddingValues,
                        eventDetailsViewModel,
                        eventId!!
                    )
                }
                Routes.Timeline.route -> {
                    TimelineScreen(paddingValues, timelineViewModel)
                }

            }
        }
        return PaddingValues()
    }
}