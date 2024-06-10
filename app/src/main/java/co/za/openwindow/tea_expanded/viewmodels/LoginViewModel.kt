package co.za.openwindow.tea_expanded.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.za.openwindow.tea_expanded.repositories.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//ViewModel = Logic of our views (performs the functionality)
class LoginViewModel(private val authRepository: AuthRepository = AuthRepository()):ViewModel() {

    private val _authState = MutableStateFlow(AuthUiState()) //only this view model can change the values, and nothing else
    val authState:StateFlow<AuthUiState> = _authState //variable we'll use in the view

    //TODO: Handle the input changes from the user

    //TODO: show error message
    //TODO: navigate to home when successful
    //access to the email and password that the user typed in the VIEW
    fun login(){

        viewModelScope.launch { // async/await
            try {
                authRepository.loginUser(_authState.value.email, _authState.value.password){isCompleted ->
                    if (isCompleted){
                        Log.d("AAA Current User", Firebase.auth.currentUser?.email.toString())
//                        Firebase.auth.currentUser
                    } else {
                        Log.d("AAA Error Login user repo: ", "Something went wrong")
                    }
                }
            } catch (e: Exception) {
                Log.d("AAA Error Login User: ", e.localizedMessage.toString())
            }
        }

    }
}

//Create a data class - represent all the variables I have in my View
data class AuthUiState(
    val email: String = "test@gmail.com",
    val password : String = "123456", //6 character long requirement
    val success: Boolean = true,
    val error: String = "",
)