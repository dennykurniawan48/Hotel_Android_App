package com.skripsi.portofoliohotel.Screen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.skripsi.portofoliohotel.Model.kota.AllKota
import com.skripsi.portofoliohotel.Model.kota.Data
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel
import com.skripsi.portofoliohotel.viewmodel.HomeViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.skripsi.portofoliohotel.Model.Constant
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Navigation.Screen
import java.text.DecimalFormat

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    val items = mutableListOf(Data("", 0, "Semua Kota", ""))
    var selectedKota by rememberSaveable { mutableStateOf(0) }
    var refreshdata by remember { mutableStateOf(false) }
    var firstLoad by rememberSaveable { mutableStateOf(true) }
    val allKota by homeViewModel.allKotaResponse.observeAsState()
    val dataHotel by homeViewModel.allHotelResponse.observeAsState()
    var expandedKota by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = firstLoad){
        if(firstLoad) {
            homeViewModel.getAllHotel()
            homeViewModel.getAllKota()
            firstLoad = false
        }
    }
    LaunchedEffect(key1 = refreshdata){
        if(refreshdata){
            if(items[selectedKota].id == 0){
                homeViewModel.getAllHotel()
            }else{
                homeViewModel.getHotelbyLokasi(items[selectedKota].id)
            }
            refreshdata = false
        }
    }


    if(allKota is NetworkResult.Success){
        items.clear()
        items.add(Data("", 0, "Semua Kota", ""))
        allKota!!.data?.data?.forEach {
            items.add(it)
        }
    }

    Scaffold(
        content = {
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = Color.Gray)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            items[selectedKota].nama,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    expandedKota = true
                                })
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    DropdownMenu(
                        expanded = expandedKota,
                        onDismissRequest = {
                            expandedKota = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White
                            )
                    ) {
                        Log.d("Kota", items.toString())
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedKota = index
                                expandedKota = false
                                refreshdata = true
                            }) {
                                Text(text = items[index].nama)
                            }
                        }
                    }
                }

                if(dataHotel is NetworkResult.Error){
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text(text = "Error Loading Data...", style = MaterialTheme.typography.h5, color= Color.Gray)
                        Button(onClick = { refreshdata = true }) {
                            Text(text = "Refresh")
                        }
                    }
                }

                if(dataHotel is NetworkResult.Loading){
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text(text = "Loading Data...", style = MaterialTheme.typography.h5, color= Color.Gray)
                    }
                }

                if(dataHotel is NetworkResult.Success) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                            items(dataHotel!!.data?.data ?: emptyList()) { item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp), elevation = 5.dp
                                ) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                        navController.navigate(
                                            Screen.Detail.route.replace(
                                                "{id}",
                                                item.id.toString()
                                            )
                                        ) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        }
                                    ) {
                                        val dec = DecimalFormat("#,###.00")
                                        val price = dec.format(item.biaya)
                                        Image(
                                            painter = rememberImagePainter(Constant.ASSET_URL + item.foto),
                                            contentDescription = item.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(1f)
                                        )
                                        Log.d("Gambar", Constant.ASSET_URL + item.foto)
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.body2,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                imageVector = Icons.Filled.LocationOn,
                                                contentDescription = "Icon",
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = item.alamat,
                                                style = MaterialTheme.typography.caption,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                        Text(
                                            text = "Rp. $price",
                                            style = MaterialTheme.typography.caption,
                                            maxLines = 1,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    )
}