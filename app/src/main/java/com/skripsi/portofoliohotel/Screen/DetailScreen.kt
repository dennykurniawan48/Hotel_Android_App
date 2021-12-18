package com.skripsi.portofoliohotel.Screen

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.skripsi.portofoliohotel.Model.Constant
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.allhotel.Data
import com.skripsi.portofoliohotel.Navigation.Screen
import com.skripsi.portofoliohotel.ui.theme.TitleOnBoardColor
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel
import com.skripsi.portofoliohotel.viewmodel.HomeViewModel
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    idHotel: Int
) {
    var firstLoad by remember{ mutableStateOf(true) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    val dataBooking by homeViewModel.dataBooking.observeAsState()
    val token by authViewModel.token.collectAsState(initial = "")

    LaunchedEffect(key1 = firstLoad){
        if(firstLoad){
            homeViewModel.getDetailById(idHotel)
            firstLoad = false
        }
    }

    LaunchedEffect(key1 = dataBooking){
        if(dataBooking is NetworkResult.Success){
            navController.navigate(Screen.Success.route)
        }
    }

    val detailHotel by homeViewModel.detailHotel.observeAsState()

    Log.d("Oke", detailHotel.toString())

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            24.dp
        else
            0.dp
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 80.dp,
        sheetContent = {
                       if(detailHotel is NetworkResult.Success){
                           detailHotel!!.data?.data?.let {
                               BottomSheetContent(selectedHero = it, token = token, homeViewModel = homeViewModel)
                           }
                       }
//            selectedHero?.let { BottomSheetContent(selectedHero = it) }
        },
        content = {
            if(detailHotel is NetworkResult.Success){
                detailHotel!!.data?.data?.let {
                    BackgroundContent(
                        heroImage = it.foto,
                        imageFraction = currentSheetFraction,
                        onCloseClicked = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    )
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@ExperimentalCoilApi
@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "${Constant.ASSET_URL}${heroImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(com.skripsi.portofoliohotel.R.drawable.onboard1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + 0.4f)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = 10.dp),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedHero: com.skripsi.portofoliohotel.Model.detail.Data,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.TitleOnBoardColor,
    token: String,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    val c: Date = Calendar.getInstance().getTime()
    val tomorrow = Calendar.getInstance()
    val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    val formattedDate: String = df.format(c)
    tomorrow.add(Calendar.DATE, 1)
    val tomorrowDate: String = df.format(tomorrow.time)

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    var startDate by remember{mutableStateOf(formattedDate)}
    var endDate by remember{mutableStateOf(tomorrowDate)}

    var datePickerStart = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val dayMonth = if(dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth
            val vMonth = if(month < 10) "0${month+1}" else month+1
            startDate = "$year/$vMonth/$dayMonth"
        }, year, month, day
    )
    datePickerStart.datePicker.minDate = c.time

    val datePickerEnd = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val dayMonth = if(dayOfMonth < 10) "0${dayOfMonth+1}" else dayOfMonth+1
            val vMonth = if(month < 10) "0${month+1}" else month+1
            endDate = "$year/$vMonth/$dayMonth"
        }, year, month, day
    )
    datePickerEnd.datePicker.minDate = tomorrow.time.time

    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = 40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Fasilitas",
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = 16.dp),
            text = selectedHero.fasilitas,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 7
        )

        val dec = DecimalFormat("#,###.00")
        val price = dec.format(selectedHero.biaya)

        Text(text = "Biaya:", fontWeight = FontWeight.Bold)
        Text(text = "Rp. $price",modifier = Modifier
            .padding(bottom = 16.dp))

        Row(modifier=Modifier.fillMaxWidth()) {
            Text(text = "Check In:", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(0.5f))
            Text(text = "Check Out:", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(0.5f))
        }


//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier=Modifier.fillMaxWidth(0.5f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = startDate, modifier = Modifier.fillMaxWidth(0.7f))
                        IconButton(onClick = {
                            datePickerStart.show()
                        }, modifier = Modifier.fillMaxWidth(0.3f)) {
                            Icon(
                                painter = painterResource(id = com.skripsi.portofoliohotel.R.drawable.ic_date),
                                contentDescription = "Tanggal"
                            )
                        }
                    }
                    Row(modifier=Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = endDate, modifier = Modifier.fillMaxWidth(0.7f))
                        IconButton(onClick = {
                            datePickerEnd.show()
                        }, modifier = Modifier.fillMaxWidth(0.3f)) {
                            Icon(
                                painter = painterResource(id = com.skripsi.portofoliohotel.R.drawable.ic_date),
                                contentDescription = "Tanggal"
                            )
                        }
                    }
                }

//            Row(
//                modifier = Modifier.weight(1f),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {

        Button(onClick = {

            val myFormat = SimpleDateFormat("yyyy/MM/dd")
            try {
                val date1 = myFormat.parse(startDate)
                val date2 = myFormat.parse(endDate)
                val time = date2.time - date1.time
                val diff = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)
                if(diff >= 1){
                    homeViewModel.postBooking(ref_hotel = selectedHero.id, start = startDate, end = endDate, durasi = diff, total = selectedHero.biaya*diff, token = token)
                }else{
                    Toast.makeText(context, "Tanggal yang anda pilih salah", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }, modifier=Modifier.fillMaxWidth()) {
            Text(text = "Booking")
        }

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=${selectedHero.latitude},${selectedHero.longitude}")
            )
            context.startActivity(intent)
        }, modifier=Modifier.fillMaxWidth()) {
            Text(text = "Open maps")
        }
    }
}