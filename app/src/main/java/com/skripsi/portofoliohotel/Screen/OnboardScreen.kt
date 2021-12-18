package com.skripsi.portofoliohotel.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.skripsi.portofoliohotel.Model.OnboardData
import com.skripsi.portofoliohotel.Navigation.Screen
import com.skripsi.portofoliohotel.ui.theme.*
import com.skripsi.portofoliohotel.viewmodel.AuthViewModel

@ExperimentalPagerApi
@Composable
fun OnboardScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val dataHotel = listOf(
        OnboardData.Board1,
        OnboardData.Board2,
        OnboardData.Board3
    )

    val state = rememberPagerState()

    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.OnBoardColor)) {
        HorizontalPager(modifier = Modifier.weight(10f),count = dataHotel.size, state = state) { pos ->
            PagerScreen(onboardData = dataHotel[pos])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = state,
            indicatorWidth = 12.dp,
            spacing = 8.dp,
            activeColor = MaterialTheme.colors.ActiveIndicator,
            inactiveColor = MaterialTheme.colors.InactiveIndicator
        )
        FinishButton(state = state, onClick = {
            authViewModel.onBoardFinished()
            navController.navigate(Screen.Login.route){
                popUpTo(Screen.Splash.route){inclusive=true}
            }
        }, modifier = Modifier.weight(1f), lastPage = dataHotel.size)
    }
}

@Composable
fun PagerScreen(onboardData: OnboardData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onboardData.image),
            contentDescription = "Onboard Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = onboardData.title,
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = MaterialTheme.colors.TitleOnBoardColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp),
            text = onboardData.desc,
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = MaterialTheme.colors.DescOnBoardColor,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Composable
fun FinishButton(
    state: PagerState,
    onClick: () -> Unit,
    modifier: Modifier,
    lastPage: Int
) {
    Row() {
        AnimatedVisibility(modifier=modifier.padding(24.dp), visible = state.currentPage == lastPage-1) {
            Button(onClick = onClick) {
                Text(text = "Finish")
            }
        }
    }
}