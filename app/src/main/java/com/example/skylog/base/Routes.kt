package com.example.skylog.base

sealed class Routes(val route: String) {
    data object EventList : Routes("eventList")
    data object EventCreate : Routes("eventCreate")
    data object EventEdit : Routes("eventEdit")
    data object EventDetail : Routes("eventDetail")
}