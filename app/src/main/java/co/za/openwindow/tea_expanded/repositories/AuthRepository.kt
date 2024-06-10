package co.za.openwindow.tea_expanded.repositories

import android.util.Log
import co.za.openwindow.tea_expanded.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Repository - viewModels connection to our firebase - communication with the Backend
class AuthRepository {

    //FIREBASE FUNCTIONALITY

    //Check logged in user info
    val currentUser: FirebaseUser? = Firebase.auth.currentUser //get user auth info
    fun hasUser(): Boolean = Firebase.auth.currentUser != null //check if there is a user
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty() //get user id

    //Log user out of Firebase
    fun logOff() {
        Firebase.auth.signOut()
        Log.d("AAA Current User", "Log off success")
    }

    //TODO: register a new user
    suspend fun createNewAccount(
        username: String,
        email: String,
        password: String,
        isCompleted:(Boolean) -> Unit //Call back function - send back true or false if it is successful or not
    ) = withContext(Dispatchers.IO) {//basically an async/await

        Firebase.auth
//            for Signup
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Log.d("AAA Register State: ", username)

                    //TODO: Create user in my DB

                    //Reference var to our users collections
                    var userDB = Firebase.firestore.collection("users")

                    //starts creating document in our collection
                    it.result.user?.uid?.let { it1 ->
                        userDB.document(it1).set(
                            User(
                                id = it1,
                                email = email,
                                username = username
                            )
                        ).addOnSuccessListener {
                            Log.d("AAA Create User State", "YAY!")
                            isCompleted.invoke(true)
                        }.addOnFailureListener { e ->
                            Log.d("AAA Register State: ", e.localizedMessage.toString())
                            isCompleted.invoke(false)
                        }
                    }

                    isCompleted.invoke(true)
                } else {
                    Log.d("AAA Register State: ", it.exception?.localizedMessage.toString())
                    isCompleted.invoke(false)
                }
            }.await()
    }

    //Login an existing user
    suspend fun loginUser(
        email: String,
        password: String,
        isCompleted:(Boolean) -> Unit //Call back function - send back true or false if it is successful or not
    ) = withContext(Dispatchers.IO) {//basically an async/await

        Firebase.auth
//            for Login
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