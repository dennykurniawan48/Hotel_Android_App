package com.skripsi.portofoliohotel.Navigation

sealed class Screen(val route: String){
    object Splash: Screen("SPLASH")
    object OnBoarding: Screen("ONBOARDING")
    object Login: Screen("LOGIN")
    object Register: Screen("REGISTER")
    object Home: Screen("HOME")
    object Detail: Screen("DETAIL/{id}")
    object Success: Screen("SUCCESS")
}
