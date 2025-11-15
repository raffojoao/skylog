package com.example.skylog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.skylog.R
import com.example.skylog.ui.theme.SkyLogTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    date: String,
    time: String,
    image: String?,
    location: String,
    onEdit: (String) -> Unit = {},
    onDelete: (String) -> Unit = {},
    onClickCard: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(IntrinsicSize.Min),
        colors = CardDefaults.cardColors(containerColor = Color(0x1B263BFF)),
        onClick = { onClickCard(id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
            ) {
                if (image.isNullOrBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    GlideImage(
                        model = image,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    ) { requestBuilder ->
                        requestBuilder
                            .placeholder(R.drawable.image)
                            .error(R.drawable.image)
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$date - $time",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = location,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onEdit(id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { onDelete(id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    SkyLogTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EventCard(
                modifier = Modifier.padding(innerPadding),
                id = "1",
                name = "Eclipse Lunar",
                date = "12/10/2025",
                time = "22:00",
                location = "Curitiba-PR",
                onDelete = {},
                onEdit = {},
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/Full_moon.png/1024px-Full_moon.png"
            )
        }
    }
}
