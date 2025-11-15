package com.example.skylog.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.skylog.model.LocalData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AstronomicalEvent(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
    val imageUrl: String = ""
)

class ListEventViewModel(
    val navController: NavController
) : ViewModel() {

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _alertType = MutableStateFlow("")
    val alertType: StateFlow<String> = _alertType

    fun setAlertType(value: String) {
        _alertType.value = value
    }

    private val _events = MutableStateFlow<List<AstronomicalEvent>>(emptyList())
    val events: StateFlow<List<AstronomicalEvent>> = _events
    private val _selectedEventId = MutableStateFlow<String>("")
    val selectedEventId: StateFlow<String> = _selectedEventId

    private val db = FirebaseFirestore.getInstance()

    init {
        loadEvents()
    }

    fun setSelectedEventId(id: String) {
        _selectedEventId.value = id
    }
    fun deleteEvent(id: String) {
        db.collection("events").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                loadEvents()
                _showDialog.value = false
                _alertType.value = "success"
            }
            .addOnFailureListener {
                e ->
                Log.w(TAG, "Error deleting document", e)
                _alertType.value = "error"
            }
    }

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    fun navigate(screen: String) {
        navController.navigate(screen)
    }

    private fun loadEvents() {
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                val snapshot = db.collection("events")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                val eventList = snapshot.documents.mapNotNull { doc ->
                    AstronomicalEvent(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        date = doc.getString("date") ?: "",
                        time = doc.getString("time") ?: "",
                        location = doc.getString("location") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                }
                _events.value = eventList
            } catch (e: Exception) {
                Log.e("ListEventViewModel", "Erro ao buscar eventos", e)
            }
        }
    }
}
