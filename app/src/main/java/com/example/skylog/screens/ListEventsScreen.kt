package com.example.skylog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skylog.base.Routes
import com.example.skylog.components.EventCard
import com.example.skylog.ui.theme.SkyLogTheme

data class AstronomicalEvent(
    val id: Int,
    val name: String,
    val datetime: String,
    val image: String?,
    val location: String
)

@Composable
fun ListEventsScreen(paddingValues: PaddingValues, viewModel: ListEventViewModel) {
    val events = listOf(
        AstronomicalEvent(
            id = 1,
            name = "Eclipse Lunar Total",
            datetime = "12/10/2025 - 22:00",
            image = "https://cdn.britannica.com/00/240500-138-9DA1E69A/rarity-of-a-total-solar-eclipse.jpg",
            location = "Curitiba - PR"
        ),
        AstronomicalEvent(
            id = 2,
            name = "Chuva de Meteoros Perseidas",
            datetime = "13/08/2025 - 03:30",
            image = "",
            location = "Chapada dos Veadeiros - GO"
        ),
        AstronomicalEvent(
            id = 3,
            name = "Eclipse Solar Parcial",
            datetime = "14/12/2025 - 10:45",
            image = "https://cdn.britannica.caom/00/240500-138-9DA1E69A/rarity-of-a-total-solar-eclipse.jpg",
            location = "São Paulo - SP"
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(events) { event ->
                EventCard(
                    id = event.id,
                    name = event.name,
                    datetime = event.datetime,
                    image = event.image,
                    location = event.location,
                    onEdit = { println("Editar evento $it") },
                    onDelete = { println("Excluir evento $it") }
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

//@Preview(showBackground = true)
//@Composable
//fun ListEventsScreenPreview() {
//    SkyLogTheme {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            ListEventsScreen(
//                paddingValues = innerPadding
//            )
//        }
//    }
//}