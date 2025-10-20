package com.example.skylog.screens

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.skylog.base.Constants
import com.example.skylog.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateEventViewModel(val localData: LocalData, val navController: NavController) : ViewModel() {

    private var _title = MutableStateFlow(localData.getKey(Constants.TITLE_KEY))
    val title: StateFlow<String> = _title

    private var _description = MutableStateFlow(localData.getKey(Constants.DESCRIPTION_KEY))
    val description: StateFlow<String> = _description

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }

    fun saveTask() {
        localData.save(Constants.Companion.TITLE_KEY, _title.value)
        localData.save(Constants.Companion.DESCRIPTION_KEY, _description.value)
        navController.popBackStack()
    }
}