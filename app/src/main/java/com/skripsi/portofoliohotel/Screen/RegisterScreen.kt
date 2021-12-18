package com.skripsi.portofoliohotel.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Navigation.Screen
import com.skripsi.portofoliohotel.R
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var name by remember{ mutableStateOf("") }
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var showPassword by remember{ mutableStateOf(false) }
    val registerData by authViewModel.registerResponse.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()

    if(registerData is NetworkResult.Success){
        LaunchedEffect(key1 = true){
            navController.navigate(Screen.Login.route){
                popUpTo(Screen.Register.route){inclusive = true}
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content={
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var iconVisibilty = if(showPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                Image(
                    painter = painterResource(id = R.drawable.appslogo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(text = "Nama")
                    },
                    modifier = Modifier
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text(text = "Email")
                    },
                    modifier = Modifier
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = password,
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            start = 24.dp,
                            end = 24.dp
                        )
                        .fillMaxWidth(),
                    singleLine = true,
                    onValueChange = { password = it },
                    label = {
                        Text(text = "Password")
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                painter = painterResource(id = iconVisibilty),
                                contentDescription = "Change Visibilty"
                            )
                        }
                    }
                )
                if(registerData is NetworkResult.Error){
                    LaunchedEffect(key1 = registerData){
                        scaffoldState.snackbarHostState.showSnackbar(registerData?.message.toString())
                    }
                }
                Button(
                    onClick = {
                        if(username.isEmpty()){
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Email kosong")
                            }
                        }else if(name.isEmpty()){
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Nama kosong")
                            }
                        }else if(!username.contains("@")){
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Email tidak valid")
                            }
                        }else if(password.isEmpty()){
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Password kosong")
                            }
                        }else if(password.length < 6){
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Password min 6 karakter")
                            }
                        }else{
                            authViewModel.postRegister(email = username, password = password, name=name)
                        }
                    },
                    modifier = Modifier
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = "Register")
                }
                Button(
                    onClick = { navController.navigate(Screen.Login.route) },
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = "Kembali")
                }
            }
        }
    )

}