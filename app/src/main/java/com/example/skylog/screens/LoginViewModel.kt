package com.example.skylog.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.skylog.base.Routes
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(
    val navController: NavController
) {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    private val auth = FirebaseAuth.getInstance()


    fun login() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(route = Routes.EventList.route)
                } else {
                    errorMessage = task.exception?.message
                }
            }
    }
}