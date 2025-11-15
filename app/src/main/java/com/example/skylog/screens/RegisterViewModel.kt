package com.example.skylog.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.skylog.base.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterViewModel(
    private val navController: NavController
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun setName(value: String) { _name.value = value }
    fun setBirthDate(value: String) { _birthDate.value = value }
    fun setEmail(value: String) { _email.value = value }
    fun setPassword(value: String) { _password.value = value }
    fun setConfirmPassword(value: String) { _confirmPassword.value = value }

    fun register() {
        _errorMessage.value = null
        _successMessage.value = null

        if(name.value == "") {
            _errorMessage.value = "Insira seu nome"
            return
        }
        if (!isValidDate(birthDate.value)) {
            _errorMessage.value = "Data inválida. Use o formato dd/mm/yyyy"
            return
        }

        if (!isValidEmail(email.value)) {
            _errorMessage.value = "E-mail inválido"
            return
        }

        if(password.value == "") {
            _errorMessage.value = "Informe uma senha"
            return
        }

        if (password.value != confirmPassword.value) {
            _errorMessage.value = "As senhas não coincidem"
            return
        }

        viewModelScope.launch {
            try {
                _successMessage.value = "Cadastro criado com sucesso!"

                auth.createUserWithEmailAndPassword(email.value, password.value).await()

                val uid = auth.currentUser?.uid ?: throw Exception("Erro ao obter UID")

                val user = hashMapOf(
                    "id" to uid,
                    "name" to name.value,
                    "birthDate" to birthDate.value,
                    "email" to email.value,
                    "profilePicture" to ""
                )

                db.collection("users").document(uid).set(user).await()

                navController.navigate(Routes.EventList.route)

            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return email.matches(regex)
    }

    private fun isValidDate(date: String): Boolean {
        if (!Regex("^\\d{2}/\\d{2}/\\d{4}$").matches(date)) return false

        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
