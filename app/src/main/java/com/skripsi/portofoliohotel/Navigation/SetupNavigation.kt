package com.skripsi.portofoliohotel.Navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skripsi.portofoliohotel.Screen.*
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun SetupNavigation(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.OnBoarding.route) {
            OnboardScreen(navController, authViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, authViewModel = authViewModel, homeViewModel = hiltViewModel())
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(route = Screen.Detail.route, arguments = listOf(
            navArgument(name="id"){
                type = NavType.IntType
            }
        )){
            DetailScreen(
                navController = navController,
                authViewModel = authViewModel,
                idHotel = it.arguments?.getInt("id")!!,
                homeViewModel = hiltViewModel()
            )
        }

        composable(Screen.Success.route) {
            Success(navController)
        }
    }
}