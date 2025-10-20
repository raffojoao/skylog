package com.example.skylog.screens

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.skylog.base.Constants
import com.example.skylog.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListEventViewModel(val localData: LocalData, val navController: NavController) : ViewModel() {

    private var _title = MutableStateFlow(localData.getKey(Constants.TITLE_KEY))
    val title: StateFlow<String> = _title

    private var _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    fun deleteTask() {
        localData.delete(Constants.TITLE_KEY)
        localData.delete(Constants.DESCRIPTION_KEY)
        _title.value = ""
        _showDialog.value = false
    }

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    fun navigate(screen: String) {
        navController.navigate(screen)
    }
}