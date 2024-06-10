package co.za.openwindow.tea_expanded.repositories

import android.util.Log
import co.za.openwindow.tea_expanded.models.Chats
import com.google.firebase.Firebase
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

const val conversationDB = "chats" //match the collection name in db
class ConversationRepository {

    val db = Firebase.firestore

    private val conversationCollection = db.collection(conversationDB)

    suspend fun getAllConversations(
        onComplete: (List<Chats>?) -> Unit
    ){

        Log.d("AAA Getting chats...", "loading...")

        var conversations: ArrayList<Chats> = arrayListOf<Chats>()

        //get all the data
        conversationCollection.get()
            .addOnSuccessListener { documents ->
                //loop through each document and conform it to add to our array of conversations
                for(doc in documents) {
                    conversations.add(
                        Chats(
                            id = doc.id,
                            participant = doc.data["participant"].toString(),
                            date = doc.data["date"].toString(),
                            message = doc.data["message"].toString(),
                            participantImg = doc.data["participantImg"].toString(),
                        )
                    )
                }

                Log.d("AAA Amount of data", conversations.size.toString())
                onComplete.invoke(conversations)
            }.addOnFailureListener{ e ->
                Log.d("AAA Error with getting conversations", e.localizedMessage.toString())
                onComplete.invoke(null)
            }

    }

}