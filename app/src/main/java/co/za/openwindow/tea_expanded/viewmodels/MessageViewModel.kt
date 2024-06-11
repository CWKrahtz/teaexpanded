package co.za.openwindow.tea_expanded.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import co.za.openwindow.tea_expanded.models.Chats
import co.za.openwindow.tea_expanded.models.Message
import co.za.openwindow.tea_expanded.repositories.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject



class MessageViewModel(
    chatsId: String = "1234",
    private val authRepository: AuthRepository = AuthRepository()
): ViewModel() {

    //mutableSTATE - state is observed and die ui updates if it changes
    private var _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    //define our listener for realtime data
    var messageListener: ListenerRegistration? = null

    //Runs when viewmodel loads
    init {
        getRealTimeMessages(chatsId)
    }

    //function that trigger real time listener
    fun getRealTimeMessages(id: String){

        Log.d("AAA Listening for reminders...", "...")

        //define what it is we want to listen to
        //Messages -> messageID -> message collection
        val messageRef = Firebase.firestore
            .collection("chats")
            .document(id)
            .collection("messages")

        //snapshot = refers to the data that gets returns -> listening to the snapshot changes
        messageListener = messageRef.addSnapshotListener { snapshot, error ->
            Log.d("AAA Listening Start", "...")

            if(error != null){
                Log.d("AAA Listening Error", error.localizedMessage.toString())
                return@addSnapshotListener //ending the listening
            }

            if(snapshot != null){
                Log.d("AAA listening received", snapshot.size().toString())

                //checking how the data changed, and only adds a new message if a document was added
                for (dc in snapshot!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> _messageList.add(dc.document.toObject(Message::class.java))
                        DocumentChange.Type.MODIFIED -> Log.d("Document Modified...", "Modified: ${dc.document.data}")
                        DocumentChange.Type.REMOVED -> Log.d("Document Deleted...", "Removed: ${dc.document.data}")
                    }
                }
//                _messageList.clear() <- option 1 - to much load time
//                for(document in snapshot){
//                    _messageList.add(document.toObject(Message::class.java))
//                }
            }


        }

    }

    fun addNewMessage(message: String, chatsId: String){
        val messageRef = Firebase.firestore
            .collection("chats")
            .document(chatsId)
            .collection("messages")

        //get the user's information
        val user = authRepository.currentUser

        val newMessage = Message(
            text = message,
            from = user?.email.toString(), //TODO: get user data from firestore with current user's id
            fromUserId = user?.uid.toString(),
            time = Timestamp.now()
        )

        messageRef.add(newMessage)
            .addOnSuccessListener {
                Log.d("AAA new message success", it.id)
            }
            .addOnFailureListener {
                Log.d("AAA new message error", it.localizedMessage.toString())
            }
    }

//    fun stopListening(){
//        messageListener?.remove()
//        messageListener = null
//    }

}