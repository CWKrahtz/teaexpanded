package co.za.openwindow.tea_expanded.Screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.za.openwindow.tea_expanded.R
import co.za.openwindow.tea_expanded.ui.theme.TeaexpandedTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(24.dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.floppydisk),
                contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        var username by remember { mutableStateOf("Username") }
        OutlinedTextField(
            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 40.sp),
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
//            label = { Text("Username") },
//            trailingIcon = { Icon( Icons.Default.Edit, contentDescription ="Edit Username" ) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        var email by remember { mutableStateOf("Email") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            trailingIcon = { Icon( Icons.Default.Edit, contentDescription ="Edit Email" ) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        var password by remember { mutableStateOf("Password") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text  = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(painter = painterResource(id = R.drawable.eye), contentDescription = "Eye") },
        )

        Spacer(modifier = Modifier.height(24.dp))
        Column (
//            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top

        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF179CDE))
            ) {
                Text(
                    text = "Update Changes",
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