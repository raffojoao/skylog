package com.example.skylog.model

data class EventDetailsUiState(
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
    val imageUrl: String? = null,
    val postedByName: String = "",
    val postedByProfilePicture: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
