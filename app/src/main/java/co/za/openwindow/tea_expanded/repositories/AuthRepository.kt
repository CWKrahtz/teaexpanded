package co.za.openwindow.tea_expanded.repositories

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Repository - viewModels connection to our firebase - communication with the Backend
class AuthRepository {

    //FIREBASE FUNCTIONALITY

    //TODO: Check logged in user info

    //TODO: register a new user

    //Login an existing user
    suspend fun loginUser(
        email: String,
        password: String,
        isCompleted:(Boolean) -> Unit //Call back function - send back true or false if it is successful or not
    ) = withContext(Dispatchers.IO) {//basically an async/await

        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Log.d("AAA Login State: ", "success")
                    isCompleted.invoke(true)
                } else {
                    Log.d("AAA Login State: ", it.exception?.localizedMessage.toString())
                    isCompleted.invoke(false)
                }
            }.await()

    }

}