package co.za.openwindow.tea_expanded.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.za.openwindow.tea_expanded.models.Chats
import co.za.openwindow.tea_expanded.repositories.ConversationRepository
import kotlinx.coroutines.launch

class ConversationsViewModel (private val conversationRepository: ConversationRepository = ConversationRepository()): ViewModel() {

    //mutableSTATE - state is observed and die ui updates if it changes
    private var _conversationList = mutableStateListOf<Chats>()
    val conversationList: List<Chats> = _conversationList

    init {
        //runs when view model is initialised
        getAllConversations()
    }

    private fun getAllConversations() = viewModelScope.launch{

        conversationRepository.getAllConversations {
            if (it != null) {
                for (doc in it){
                    _conversationList.add(doc)
                }
            }
        }
    }

}