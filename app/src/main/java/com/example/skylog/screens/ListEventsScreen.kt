package com.example.skylog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skylog.base.Routes
import com.example.skylog.components.EventCard
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

@Composable
fun ListEventsScreen(paddingValues: PaddingValues, viewModel: ListEventViewModel) {
    val events by viewModel.events.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()
    val eventId by viewModel.selectedEventId.collectAsState()
    val alertType by viewModel.alertType.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .padding(paddingValues)
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = {
                        viewModel.deleteEvent(eventId)
                    }) {
                        Text(text = "Sim")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        viewModel.setShowDialog(false)
                    }) {
                        Text(text = "Não")
                    }
                },
                text = { Text(text = "Deseja excluir este evento?") }
            )
        }
        if (alertType.isNotEmpty()) {
            val message = when (alertType) {
                "success" -> "Evento excluído com sucesso!"
                "error" -> "Erro ao excluir o evento. Tente novamente."
                else -> ""
            }
            AlertDialog(
                onDismissRequest = { viewModel.setAlertType("") },
                confirmButton = {
                    Button(onClick = { viewModel.setAlertType("") }) {
                        Text("OK")
                    }
                },
                text = { Text(message) }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(events) { event ->
                EventCard(
                    id = event.id,
                    name = event.title,
                    date = event.date,
                    time = event.time,
                    image = event.imageUrl,
                    location = event.location,
                    onEdit = { viewModel.navigate(Routes.EventCreate.createRoute(it)) },
                    onDelete = {
                        viewModel.setSelectedEventId(it)
                        viewModel.setShowDialog(true)
                    },
                    onClickCard = {
                        viewModel.navigate(
                            Routes.EventDetails.createRoute(it)
                        )
                    }
                )
            }
        }
        FloatingActionButton(
            onClick = { viewModel.navigate(Routes.EventCreate.route) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+", fontSize = 20.sp)
        }
    }

}