package co.za.openwindow.classworkreminder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.za.openwindow.tea_expanded.models.Message
import co.za.openwindow.tea_expanded.ui.theme.Purple40
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.viewmodels.MessageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import co.za.openwindow.tea_expanded.R
import com.google.firebase.Firebase

//MessageScreen
@Composable
fun MessageScreen(
    messageId: String = "noId",
    viewModel: MessageViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    var message by remember { mutableStateOf("") }

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
        Box(
            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
        ){
            //Style to display button next-to the TextField
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Your Message") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color(0x80B1B1B1))
            )

            //Convert into a send image/icon
            Button(
                onClick = { viewModel.addNewMessage(message, messageId); message = "" },
                modifier = modifier
                    .height(50.dp)
                    .align(alignment = Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0x00FFFFFF))
            ) {
//            Text("Send Message")
                Image(
                    painter = painterResource(id = R.drawable.send_black),
                    contentDescription = "",
                    modifier = Modifier
//                    .clip(RoundedCornerShape(100.dp))
                        .size(25.dp),
                    contentScale = ContentScale.Crop
                )
            }
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
                color = Color(0xFF179CDE),
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
    ) {
        Text(
            text = message.username,  // Change this line to use username
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