package co.za.openwindow.tea_expanded.models

data class Chats (
    val id: String, //Firebase saves ids as image
    val participant: String,//Conversation with
    val participantImg: String,//Other person's profile image
    val date: String,//time last message received - day, if previous day or later
    val message: String//last message send/received
)