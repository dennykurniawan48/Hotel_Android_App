package com.skripsi.portofoliohotel.Screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skripsi.portofoliohotel.Model.booking.Data
import com.skripsi.portofoliohotel.Navigation.Screen
import kotlinx.serialization.json.Json

@Composable
fun Success(
    navController: NavHostController
) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(16.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Row(){
                IconButton(onClick = { navController.popBackStack(Screen.Detail.route, true) }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "", tint = Color.Gray, modifier = Modifier.size(25.dp))
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "",
                    tint = Color.Green,
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(text = "Pesanan berhasil",
                fontSize = 25.sp)
            Text(text = "Silahkan untuk melakukan pembayaran di hotel yang bersangkutan untuk proses selanjutnya")
        }
    }

    BackHandler(enabled = true){
        navController.popBackStack(Screen.Detail.route, true)
    }
}