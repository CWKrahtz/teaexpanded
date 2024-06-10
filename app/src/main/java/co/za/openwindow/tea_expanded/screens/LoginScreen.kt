package co.za.openwindow.tea_expanded.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.ui.theme.DarkBlue
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.ui.theme.White
import co.za.openwindow.tea_expanded.viewmodels.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

//VIEW - What the user sees, and interact with

//private lateinit var auth: FirebaseAuth

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navigateToSignup:() -> Unit = {},
    modifier: Modifier = Modifier
) {

    //TEST - firebase do not keep in code
    // Initialize Firebase Auth
//    auth = Firebase.auth
//
//    // Check if user is signed in (non-null) and update UI accordingly.
//    val currentUser = auth.currentUser
//    if (currentUser != null) {
//        Log.d("AAA Current User", currentUser.email.toString())
//    }else {
//        Log.d("AAA Current User", "NONE")
//    }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }

    val loginState by viewModel.authState.collectAsState()//link our viewModel to our view

    Column (
        Modifier
            .background(White)
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 50.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.tea),
            contentDescription = "Logo",
            alignment = Alignment.Center,
            modifier = Modifier
                .width(75.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(CornerSize(50.dp)),
                )
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Welcome Back",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Login to your account or ",
            )
            TextButton(
                onClick = { navigateToSignup.invoke() },
                contentPadding = PaddingValues(0.dp)
                ) {
                Text(
                    text = "Create New Account",
                    textDecoration = TextDecoration.Underline,
                    color = DarkBlue
                )
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = loginState.email,
            onValueChange = { /*TODO: Handle on state change*/ },
            label = { Text("Email") },
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = loginState.password,
            onValueChange = { /*TODO: Handle on state change*/ },
            label = { Text(text  = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(painter = painterResource(id = R.drawable.eye), contentDescription = "Eye") },
            )

        Spacer(modifier = Modifier.height(24.dp))
        Column (
            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom

        ){
            Button(onClick = { viewModel.login() }, //login function in viewModel
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF179CDE))
            ) {
                Text(
                    text = "Login",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TeaexpandedTheme {
        LoginScreen()
    }
}