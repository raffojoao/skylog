package com.example.skylog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.skylog.R


@Composable
fun EventDetailsScreen(
    paddingValues: PaddingValues,
    viewModel: EventDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    eventId: String,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    if (state.isLoading) {
        CircularProgressIndicator()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            val profilePic = state.postedByProfilePicture

            if (profilePic != null) {
                Image(
                    painter = rememberAsyncImagePainter(profilePic),
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painterResource(R.drawable.image),
                    contentDescription = "Foto padrão",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Postado por ${state.postedByName}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(16.dp))

        state.imageUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(state.title, style = MaterialTheme.typography.headlineSmall)
        Text("Local: ${state.location}")
        Text("Data: ${state.date} às ${state.time}")

        Spacer(Modifier.height(16.dp))

        Text(state.description)
    }
}
