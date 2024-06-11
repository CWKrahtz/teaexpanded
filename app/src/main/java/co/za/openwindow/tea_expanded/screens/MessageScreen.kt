package co.za.openwindow.classworkreminder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.za.openwindow.tea_expanded.models.Message
import co.za.openwindow.tea_expanded.ui.theme.Purple40
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.viewmodels.MessageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//MessageScreen
@Composable
fun MessageScreen(
    messageId: String = "noId",
    viewModel: MessageViewModel = viewModel(),
    modifier: Modifier = Modifier
) {



    var message by remember { mutableStateOf("") }

    val dummyData = listOf<Message>(// -> reminder == messages
        Message(from = "Armand", text = "The very last reminder", fromUserId = "Armand"),
        Message(from = "My Friend", text = "I love Dev!", fromUserId = "MyFriend"),
    )

    val messageData = viewModel.messageList

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = messageId,
            modifier = modifier
        )

        LazyColumn(
            modifier = modifier.weight(1f)
        ) {
            items(messageData) { message ->
                //Style message Bubble to suite your app's look and feel
                MessageBubble(message)
            }
        }
        //Style to display button next-to the TextField
        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Your Message") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = modifier.fillMaxWidth()
        )

        //Convert into a send image/icon
        Button(
            onClick = { viewModel.addNewMessage(message, messageId); message = "" },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Send Message")
        }
    }

}

@Composable
fun MessageBubble(
    message: Message, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = Purple40,
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
    ) {
        Text(
            text = message.from,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier.padding(start = 10.dp, top = 10.dp)
        )
        Text(
            text = message.text,
            color = Color.White,
            modifier = modifier.padding(start = 10.dp, top = 2.dp)
        )
        Text(
            text = message.time.toDate().toString(),
            color = Color.LightGray,
            modifier = modifier
                .padding(all = 10.dp)
                .align(Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderPreview() {
    TeaexpandedTheme {
        MessageScreen()
    }
}