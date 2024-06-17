package co.za.openwindow.tea_expanded.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.repositories.AuthRepository
import co.za.openwindow.tea_expanded.services.MyNotification
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(
    logUserOff: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    //TODO: old img <- set to logged in user's old image
    var oldImage by remember { mutableStateOf<Uri?>(null) }

    // Fetch and log user data
    LaunchedEffect(key1 = true) {  // key1 = true ensures this block runs only once
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val uid = user.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        Log.d("AAA ProfileScreen", "User Data: ${document.data}")
                        username = document.data?.get("username") as? String ?: ""
                        email = document.data?.get("email") as? String ?: ""
                        val imageUrl = document.data?.get("profileImg") as? String
                        oldImage = imageUrl?.let { Uri.parse(it) }
                        Log.d("AAA Profile Image", oldImage.toString())
                    } else {
                        Log.d("AAA ProfileScreen", "No such user")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("AAA ProfileScreen", "Error getting user data: ", exception)
                }
        }
    }
    

    //TODO: 1. Setup the selection of an image with JETPACK COMPOSE
    var selectedImageUrl by remember {
        mutableStateOf<Uri?>(null)
    }

    val launchImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUrl = it
        })


    //TODO: 2. Upload selected image to firebase storage
    //SELF NOTE: better to do in viewModel.

    val coroutineScope = rememberCoroutineScope()

    suspend fun saveData() {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val storageRef = Firebase.storage.reference
        val userId = auth.currentUser?.uid

        try {
            // Upload image and get download URL
            val downloadUrl = selectedImageUrl?.let {
                storageRef.child("profiles/$userId/profileImg.jpg")
                    .putFile(it).await()
                    .storage.downloadUrl.await()
            }

            // Log the success of image upload
            Log.d("AAA Upload success...", downloadUrl.toString())

            // Update Firestore with new user data
            userId?.let {
                val userUpdates = hashMapOf<String, Any>(
                    "username" to username,
                    "email" to email
                )
                downloadUrl?.let { url ->
                    userUpdates["profileImg"] = url.toString()
                }

                firestore.collection("users").document(it)
                    .set(userUpdates, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("AAA Firestore Update", "User data updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("AAA Firestore Update", "Error updating user data", e)
                    }
            }
        } catch (e: Exception) {
            Log.e("AAA Upload Image Error", e.localizedMessage ?: "Error")
        }
    }



    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ){
        Spacer(modifier = Modifier.height(24.dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (selectedImageUrl != null){
                AsyncImage(
                    model = selectedImageUrl,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )
            } else if (oldImage.toString() != "" && oldImage != null){
                //or just ELSE to show old image by default
                AsyncImage(
                    model = oldImage,
                    contentDescription = "Old Image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        OutlinedTextField(
            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 40.sp),
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            trailingIcon = { Icon( Icons.Default.Edit, contentDescription ="Edit Email" ) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column (
            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom

        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                          launchImagePicker.launch(
                              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                          )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF179CDE)
                )
            ) {
                Text(
                    text = "Select an Image",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        saveData()
                    };
                    var notification = MyNotification(context)
                    notification.showNotification(
                        "Profile Information",
                        "User Information Updated."
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C8C8C)
                )
            ) {
                Text(
                    text = "Save Information",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { logUserOff.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)
                )
            ) {
                Text(
                    text = "Sign Out",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TeaexpandedTheme {
        ProfileScreen()
    }
}