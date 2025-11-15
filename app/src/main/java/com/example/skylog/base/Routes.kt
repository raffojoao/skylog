package com.example.skylog.base

sealed class Routes(val route: String) {
    data object EventList : Routes("eventList")

    data object EventCreate : Routes("eventCreate") {
        fun createRoute(eventId: String? = null): String {
            return if (eventId != null) {
                "eventCreate?eventId=$eventId"
            } else "eventCreate"
        }
    }

    data object Home : Routes("home")
    data object Register : Routes("register")
    data object Login : Routes("login")

    data object EventDetails : Routes("eventDetails") {
        fun createRoute(eventId: String): String {
            return "eventDetails?eventId=$eventId"
        }
    }
    data object Tabs : Routes("tabs")
    data object Timeline : Routes("timeline")
}
