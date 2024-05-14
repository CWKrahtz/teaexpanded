package co.za.openwindow.tea_expanded.views

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.models.Chats
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import coil.compose.AsyncImage

@Composable
fun ConversationView(
    chats: Chats = Chats(
        id = "automated chat id",
        participant = "Julius Krahtz",
        participantImg = "url...",
        date =  "YYYY/MM/DD",
        message = "last message send or Last message received."
    ),
    modifier: Modifier = Modifier ) {

    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ){
        Row (
            modifier = modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            //Add Image
            AsyncImage(
                model = chats.participantImg,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "The Classroom image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop, //so that our image does not stretch

            )
            Spacer(modifier.size(10.dp))
            Column (
                modifier = Modifier.height(50.dp)
                    .width(220.dp),
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = chats.participant,
                    fontSize = 20.sp,
                )
                Text(
                    text = chats.message,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column (
                Modifier.width(110.dp)
                    .height(50.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = chats.date,
                    fontSize = 12.sp,
                )
            }

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