package co.za.openwindow.tea_expanded

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.za.openwindow.classworkreminder.screens.MessageScreen
import co.za.openwindow.tea_expanded.screens.ConversationScreen
import co.za.openwindow.tea_expanded.screens.LoginScreen
import co.za.openwindow.tea_expanded.screens.ProfileScreen
import co.za.openwindow.tea_expanded.screens.SignupScreen
import co.za.openwindow.tea_expanded.viewmodels.AuthViewModel
import co.za.openwindow.tea_expanded.viewmodels.MessageViewModel

//Define all my nav links
object AuthRoutes { //if Logged out
    const val loginScreen = "login"
    const val signupScreen = "signup"
}
object HomeRoutes { //if Logged in
    const val conversationScreen = "conversation"
    const val profileScreen = "profile"
    const val messageScreen = "message"
}

///Manage all our navigation
@Composable
fun Navigation(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

//      Add functionality to check which route to start with
//      Check the current user to determine LOGIN/HOME
    val startingScreen = if(authViewModel.userState){
        HomeRoutes.conversationScreen
    } else {
        AuthRoutes.loginScreen
    }

//    Router - Define all our composable nav routes
    NavHost(navController = navController, startDestination = startingScreen){

//        Define all our screens that can be navigated to
        //AuthScreens
        composable(route = AuthRoutes.loginScreen){
            LoginScreen(
                navigateToSignup = {
                    navController.navigate(AuthRoutes.signupScreen)
                },
                navigateToHome = {
                    navController.navigate(HomeRoutes.conversationScreen){
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.loginScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthRoutes.signupScreen){
            SignupScreen(
                navigateBackToLogin = {
                    navController.popBackStack()
                },
                navigateToHome = {
                    navController.navigate(HomeRoutes.conversationScreen){
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.loginScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }
        //HomeScreens
        composable(route = HomeRoutes.conversationScreen){
            ConversationScreen(
                navigateToprofile = {
                    navController.navigate(route = HomeRoutes.profileScreen){
                        launchSingleTop = true
                    }
                },
                navigateToMessage = {
                    navController.navigate(route = "${HomeRoutes.messageScreen}/${it}") //passing the param to the route as defined in the compose
                }
            )
        }

        //MessageScreen of a specific conversation
        //message/12345 <- ID of message <- route param
        //argument <- explain what the param is
        composable(
            route = "${HomeRoutes.messageScreen}/{messageId}",
            arguments = listOf(navArgument("messageId") { type = NavType.StringType; defaultValue = "message12345" })
        ){
            MessageScreen(
                messageId = it.arguments?.getString("messageId").toString(),
                viewModel = MessageViewModel(chatsId = it.arguments?.getString("messageId").toString())
            )
        }

        //ProfileScreen
        composable(route = HomeRoutes.profileScreen){
            ProfileScreen(
                logUserOff = {
                    authViewModel.signUserOut()
                    navController.navigate(AuthRoutes.loginScreen){
                        launchSingleTop = true //can only have one login screen in stack
                        popUpTo(route = HomeRoutes.conversationScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }

    //Setup the request to send notifications
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        Log.d("AAA Notification request", it.toString())
    }

    LaunchedEffect(key1 = permissionLauncher) {
        //Launching our request, if not asked yet
        Log.d("AAA Launching notification request...", "pending...")
        //Check if sdk version is higher than 33
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

}
