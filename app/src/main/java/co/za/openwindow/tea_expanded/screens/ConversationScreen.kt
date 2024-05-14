package co.za.openwindow.tea_expanded.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.za.openwindow.tea_expanded.models.Chats
import co.za.openwindow.tea_expanded.ui.theme.Black
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.views.ConversationView

@Composable
fun ConversationScreen( modifier: Modifier = Modifier) {

    val dummyData: List<Chats> = listOf(
        Chats(
            id = "1",
            participant = "Conversation 1",
            participantImg = "url...",
            date =  "17:48",
            message = "Last message send! Good luck!"
        ),
        Chats(
            id = "2",
            participant = "Conversation 2",
            participantImg = "url...",
            date =  "Yesterday",
            message = "I do hope this works. If not it is okay will sort it out."
        ),
        Chats(
            id = "3",
            participant = "Conversation 3",
            participantImg = "url...",
            date =  "Tuesday",
            message = "I do hope this works. If not it is okay will sort it out."
        ),
        Chats(
            id = "4",
            participant = "Conversation 4",
            participantImg = "url...",
            date =  "Monday",
            message = "I do hope this works. If not it is okay will sort it out."
        ),
        Chats(
            id = "5",
            participant = "Conversation 5",
            participantImg = "url...",
            date =  "Sunday",
            message = "I do hope this works. If not it is okay will sort it out."
        ),
        Chats(
            id = "6",
            participant = "Conversation 6",
            participantImg = "url...",
            date =  "Saturday",
            message = "I do hope this works. If not it is okay will sort it out."
        )
    )

    Column (
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Chats",
                fontSize = 30.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            var searchChats by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {Text("Search")},
                value = searchChats,
                onValueChange = { searchChats = it },
                shape = RoundedCornerShape(CornerSize(50.dp)),
                trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search")}
                )
        }
        Spacer(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        )
        LazyColumn (
//            modifier = Modifier.padding(20.dp)
        ){
            items(dummyData) { chats -> // Assuming you have a list to iterate over
                ConversationView(chats, modifier)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ConversationScreenPreview() {
    TeaexpandedTheme {
        ConversationScreen()
    }
}

//APP - Show all Chats

//1. class data we want
//2. know how each class should display
//3. Add these classes onto our list