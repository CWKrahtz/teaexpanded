package co.za.openwindow.tea_expanded.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.services.MyNotification
import co.za.openwindow.tea_expanded.ui.theme.DarkBlue
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.ui.theme.White
import co.za.openwindow.tea_expanded.viewmodels.LoginViewModel
import co.za.openwindow.tea_expanded.viewmodels.SignupViewModel


//TODO: SignupViewModel, authRepository for signup functionality and creating new user.


@Composable
fun SignupScreen(
    viewModel: SignupViewModel = viewModel(),
    navigateBackToLogin: () -> Unit = {},
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {}
) {

    val signupState by viewModel.authState.collectAsState()
    val context = LocalContext.current

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
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Create a new user account or "
            )
            TextButton(
                onClick = { navigateBackToLogin.invoke() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Login here",
                    textDecoration = TextDecoration.Underline,
                    color = DarkBlue
                )
            }

        }

        Spacer(modifier = Modifier.height(24.dp))
//        var username by remember { mutableStateOf("Username") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signupState.username,
            onValueChange = { viewModel.handleInputStateChanges("username", it) },
            label = { Text("Username") },
        )

        Spacer(modifier = Modifier.height(24.dp))
//        var email by remember { mutableStateOf("Email") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signupState.email,
            onValueChange = { viewModel.handleInputStateChanges("email", it) },
            label = { Text("Email") },
        )

        Spacer(modifier = Modifier.height(24.dp))
//        var password by remember { mutableStateOf("Password") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signupState.password,
            onValueChange = { viewModel.handleInputStateChanges("password", it) },
            label = { Text(text  = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(painter = painterResource(id = R.drawable.eye), contentDescription = "Eye") },
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (signupState.error.isNotBlank()){
            Text(
                text = signupState.error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        Column (
            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom

        ){
            Button(
                onClick = {
                    viewModel.signup();
                    var notification = MyNotification(context)
                    notification.showNotification(
                        "Signup",
                        "Hooray! New User created."
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF179CDE))
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        if(signupState.success) {
            navigateToHome.invoke()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    TeaexpandedTheme {
        SignupScreen()
    }
}