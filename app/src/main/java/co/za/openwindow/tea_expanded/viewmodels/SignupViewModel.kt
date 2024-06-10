package co.za.openwindow.tea_expanded.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.za.openwindow.tea_expanded.repositories.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//ViewModel = Logic of our views (performs the functionality)
class SignupViewModel(private val authRepository: AuthRepository = AuthRepository()):ViewModel() {

    private val _authState = MutableStateFlow(AuthUiStateReg()) //only this view model can change the values, and nothing else
    val authState:StateFlow<AuthUiStateReg> = _authState //variable we'll use in the view

    //Handle the input changes from the user
    //target = which state we want to change
    fun handleInputStateChanges(target: String, changedValue: String) {
        _authState.update { currentState ->
            when(target){
                "username" -> currentState.copy(username = changedValue)
                "email" ->  currentState.copy(email = changedValue)
                "password" -> currentState.copy(password = changedValue)
                else -> currentState
            }
        }
    }

    //TODO: show error message
    //TODO: navigate to home when successful
    //access to the email and password that the user typed in the VIEW
    fun signup(){

        viewModelScope.launch { // async/await
            //if email and/or password is blank - error message = add email/password
            try {
                authRepository.createNewAccount( _authState.value.username, _authState.value.email, _authState.value.password){isCompleted ->
                    if (isCompleted){
                        Log.d("AAA User Registering", Firebase.auth.currentUser?.email.toString())
                        _authState.value = AuthUiStateReg(error = "", success = true)
                        //IF register - create new user in DB
                    } else {
                        Log.d("AAA Error Login user repo: ", "Something went wrong")
                        _authState.value = AuthUiStateReg(error = "Incorrect email and/or password", success = false)
                    }
                }
            } catch (e: Exception) {
                Log.d("AAA Error Login User: ", e.localizedMessage.toString())

                if(e.localizedMessage == "The email address is badly formatted."){
                    _authState.value = AuthUiStateReg(error = e.localizedMessage.toString(), success = false)
                } else if (e.localizedMessage == "Given String is empty or null") {
                    _authState.value = AuthUiStateReg(error = "Missing email and/or password", success = false)
                }
            }
        }

    }
}

//Create a data class - represent all the variables I have in my View
data class AuthUiStateReg(
    val username: String = "",
    val email: String = "",
    val password : String = "", //6 character long requirement
    val success: Boolean = false,
    val error: String = "",
)