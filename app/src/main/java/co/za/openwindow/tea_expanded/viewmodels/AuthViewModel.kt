package co.za.openwindow.tea_expanded.viewmodels

import androidx.lifecycle.ViewModel
import co.za.openwindow.tea_expanded.repositories.AuthRepository

//Check current logged in user info and state
class AuthViewModel(private val authRepository:AuthRepository = AuthRepository()): ViewModel() {

    val currentUserInfo = authRepository.currentUser

    val userState: Boolean
        get() = authRepository.hasUser()

    fun signUserOut(){
        authRepository.logOff()
    }

}