package com.example.skylog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skylog.R


@Composable
fun TimelineScreen(
    paddingValues: PaddingValues,
    viewModel: TimelineViewModel
) {
    val events by viewModel.timeline.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .padding(paddingValues)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(events) { item ->
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "${item.userName} postou às ${item.postedAt}",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1C2541)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {

                            Text("${item.userName} postou às ${item.postedAt}",
                                color = Color.LightGray,
                                fontSize = 13.sp
                            )

                            Spacer(Modifier.height(8.dp))

                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                placeholder = painterResource(R.drawable.image),
                                error = painterResource(R.drawable.image)
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(item.title, fontSize = 18.sp, color = Color.White)
                            Text(item.description, color = Color.LightGray)
                            Text("Local: ${item.location}", color = Color.Gray)
                        }
                    }

                }
            }
        }
    }
}
