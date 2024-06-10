package co.za.openwindow.tea_expanded

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.za.openwindow.tea_expanded.screens.ConversationScreen
import co.za.openwindow.tea_expanded.screens.LoginScreen
import co.za.openwindow.tea_expanded.screens.ProfileScreen
import co.za.openwindow.tea_expanded.screens.SignupScreen

//Define all my nav links
object AuthRoutes { //if Logged out
    const val loginScreen = "login"
    const val signupScreen = "signup"
}
object HomeRoutes { //if Logged in
    const val conversationScreen = "conversation"
    const val profileScreen = "profile"
}

///Manage all our navigation
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

//    TODO: Add functionality to check which route to start with
    //TODO: Check the current user to determine LOGIN/HOME

//    Router - Define all our composable nav routes
    NavHost(navController = navController, startDestination = AuthRoutes.loginScreen){

//        Define all our screens that can be navigated to
        //AuthScreens
        composable(route = AuthRoutes.loginScreen){
            LoginScreen(
                navigateToSignup = {
                    navController.navigate(AuthRoutes.signupScreen)
                }
            )
        }
        composable(route = AuthRoutes.signupScreen){
            SignupScreen(
                navigateBackToLogin = {
                    navController.popBackStack()
                }
//                navigateToLogin = {
//                    navController.navigate(AuthRoutes.signupScreen)
//                }
            )
        }
        //HomeScreens
        composable(route = HomeRoutes.conversationScreen){
            ConversationScreen()
        }
        composable(route = HomeRoutes.profileScreen){
            ProfileScreen()
        }
    }
}
