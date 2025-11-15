package com.example.skylog.screens

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CreateEventViewModel(
    val navController: NavController,
) : ViewModel() {

    private var _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private var _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private var _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private var _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private var _time = MutableStateFlow("")
    val time: StateFlow<String> = _time
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val db = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }

    fun setLocation(value: String) {
        _location.value = value
    }

    fun setDate(value: String) {
        _date.value = value
    }

    fun setTime(value: String) {
        _time.value = value
    }


    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun saveEvent(id: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var imageUrl: String? = null
                val userId = FirebaseAuth.getInstance().currentUser!!.uid

                imageUri.value?.let { uri ->
                    val ref = storage.reference.child("events/${System.currentTimeMillis()}.jpg")
                    ref.putFile(uri).await()
                    imageUrl = ref.downloadUrl.await().toString()
                }

                val event = hashMapOf(
                    "title" to title.value,
                    "description" to description.value,
                    "location" to location.value,
                    "date" to date.value,
                    "time" to time.value,
                    "imageUrl" to imageUrl,
                    "userId" to userId,
                    "timestamp" to Timestamp.now()
                )

                if (id != null) {
                    db.collection("events").document(id)
                        .set(event, SetOptions.merge())
                        .await()
                } else {
                    db.collection("events").add(event).await()
                }

                navController.popBackStack()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao salvar evento", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getEvent(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val snapshot = db.collection("events").document(id).get().await()
                snapshot.data?.let { data ->
                    data["title"]?.let { setTitle(it as String) }
                    data["description"]?.let { setDescription(it as String) }
                    data["date"]?.let { setDate(it as String) }
                    data["time"]?.let { setTime(it as String) }
                    data["location"]?.let { setLocation(it as String) }
                    data["imageUrl"]?.let {
                        _imageUri.value = Uri.parse(it as String)
                    }
                }
            } catch (e: Exception) {
                Log.e("ListEventViewModel", "Erro ao buscar evento", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

}
