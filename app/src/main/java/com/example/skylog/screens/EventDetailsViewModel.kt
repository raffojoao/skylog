package com.example.skylog.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skylog.model.EventDetailsUiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventDetailsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val eventSnapshot = db.collection("events")
                    .document(eventId)
                    .get()
                    .await()

                val event = eventSnapshot.data ?: throw Exception("Evento não encontrado")

                val userId = event["userId"] as String

                _uiState.value = _uiState.value.copy(
                    title = event["title"] as String? ?: "",
                    description = event["description"] as String? ?: "",
                    location = event["location"] as String? ?: "",
                    date = event["date"] as String? ?: "",
                    time = event["time"] as String? ?: "",
                    imageUrl = event["imageUrl"] as String?
                )

                val userSnapshot = db.collection("users")
                    .document(userId)
                    .get()
                    .await()

                val user = userSnapshot.data

                _uiState.value = _uiState.value.copy(
                    postedByName = user?.get("name") as? String ?: "Usuário desconhecido",
                    postedByProfilePicture = user?.get("profilePicture") as? String
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
