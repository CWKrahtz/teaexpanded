package co.za.openwindow.tea_expanded.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.models.Chats
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import coil.compose.AsyncImage

@Composable
fun ConversationView(
    chats: Chats = Chats(
        id = "automated chat id",
        participant = "Name of Person",
        participantImg = "url...",
        date =  "last message time stamp",
        message = "last message send/received"
    ),
    modifier: Modifier = Modifier ) {

    Card {
        Column (
            modifier = modifier.padding(16.dp)
        ){
            //Add Image
            AsyncImage(
                model = chats.participantImg,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "The Classroom image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop //so that our image does not stretch
            )
            Spacer(modifier.size(10.dp))
            Text(
                text = chats.participant)
            Text(
                text = chats.date)
            Text(
                text = chats.message)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ConversationViewPreview() {
    TeaexpandedTheme {
        ConversationView()
    }
}