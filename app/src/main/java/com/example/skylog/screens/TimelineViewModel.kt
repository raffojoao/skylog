package com.example.skylog.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.skylog.model.TimelineItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class TimelineViewModel(
    val navController: NavController
) : ViewModel() {

    private val _timeline = MutableStateFlow<List<TimelineItem>>(emptyList())
    val timeline: StateFlow<List<TimelineItem>> = _timeline

    private val db = FirebaseFirestore.getInstance()

    init {
        loadTimeline()
    }

    fun loadTimeline() {
        viewModelScope.launch {
            try {
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                val eventsSnapshot = db.collection("events")
                    .whereNotEqualTo("userId", currentUserId)
                    .get()
                    .await()
                Log.d("TimelineViewModel", "📥 Eventos encontrados: ${eventsSnapshot.size()}")


                val items = mutableListOf<TimelineItem>()

                for (doc in eventsSnapshot.documents) {
                    Log.d("TimelineViewModel", "📄 Evento bruto: ${doc.data}")

                    val userId = doc.getString("userId") ?: ""

                    if (userId.isBlank()) {
                        Log.e("TimelineDebug", "ERRO: Evento ${doc.id} sem userId!")
                        continue
                    }

                    val userDoc = db.collection("users")
                        .document(userId)
                        .get()
                        .await()

                    val userName = userDoc.getString("name") ?: "Usuário desconhecido"

                    val ts = doc.get("timestamp") as? Timestamp
                    val hour = ts?.toDate()?.let {
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(it)
                    } ?: "--:--"

                    val item = TimelineItem(
                        id = doc.id,
                        userName = userName,
                        postedAt = hour,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        location = doc.getString("location") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )

                    items.add(item)
                }


                _timeline.value = items.sortedByDescending { it.postedAt }

            } catch (e: Exception) {
                Log.e("TimelineViewModel", "Erro ao carregar timeline", e)
            }
        }
    }
}
