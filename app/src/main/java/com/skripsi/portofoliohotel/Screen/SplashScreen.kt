package com.skripsi.portofoliohotel.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skripsi.portofoliohotel.Navigation.Screen
import com.skripsi.portofoliohotel.R
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val token by authViewModel.token.collectAsState("")
        val onBoardFinish by authViewModel.onBoardFinish.collectAsState(false)
        val isLoggedIn = !token.isNullOrEmpty()
        LaunchedEffect(key1 = isLoggedIn){
            delay(3000L)
            if(!onBoardFinish) {
               navController.navigate(Screen.OnBoarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }else{
                if (isLoggedIn) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
        Image(painter = painterResource(id = R.drawable.appslogo), contentDescription = "App Logo", modifier = Modifier.size(200.dp))
    }
}
