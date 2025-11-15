package com.example.skylog.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.skylog.base.Routes
import com.google.firebase.auth.FirebaseAuth


@Composable
fun BottomMenu(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    androidx.compose.material3.NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.EventList.route,
            onClick = { onNavigate(Routes.EventList.route) },
            label = { Text("Meus eventos") },
            icon = { Icon(Icons.Default.List, contentDescription = null) }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Timeline.route,
            onClick = { onNavigate(Routes.Timeline.route) },
            label = { Text("Eventos de amigos") },
            icon = { Icon(Icons.Default.DateRange, contentDescription = null) }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                onLogout()
            },
            icon = {
                Icon(Icons.Default.Clear, contentDescription = "Logout")
            },
            label = { Text("Sair") }
        )

    }
}

