package co.za.openwindow.tea_expanded.models

import com.google.firebase.Timestamp

data class Message (
    val text: String = "",
    val from: String = "",
    val fromUserId: String = "",
    val username: String = "",
    val time: Timestamp = Timestamp.now(),
)