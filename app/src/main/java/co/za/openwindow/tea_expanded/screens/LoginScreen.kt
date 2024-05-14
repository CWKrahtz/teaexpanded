package co.za.openwindow.tea_expanded.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
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
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.ui.theme.DarkBlue
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme
import co.za.openwindow.tea_expanded.ui.theme.White

@Composable
fun LoginScreen(name: String, modifier: Modifier = Modifier) {
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
            modifier = Modifier.width(75.dp)
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
        ){
            Text(
                text = "Login to your account or "
            )
            Text(
                text = "create new account",
                textDecoration = TextDecoration.Underline,
                color = DarkBlue
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        var email by remember { mutableStateOf("Email") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
        )

        Spacer(modifier = Modifier.height(24.dp))
        var password by remember { mutableStateOf("Password") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
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
            Button(onClick = { /*TODO*/ },
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
        LoginScreen("Android")
    }
}