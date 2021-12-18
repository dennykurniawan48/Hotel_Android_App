package com.skripsi.portofoliohotel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.allhotel.AllhotelResponse
import com.skripsi.portofoliohotel.Model.booking.Booking
import com.skripsi.portofoliohotel.Model.detail.DetailHotel
import com.skripsi.portofoliohotel.Model.kota.AllKota
import com.skripsi.portofoliohotel.Model.login.ResponseLogin
import com.skripsi.portofoliohotel.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel() {
    var allHotelResponse = MutableLiveData<NetworkResult<AllhotelResponse>>(NetworkResult.Idle())
    var allKotaResponse = MutableLiveData<NetworkResult<AllKota>>(NetworkResult.Idle())
    var detailHotel = MutableLiveData<NetworkResult<DetailHotel>>(NetworkResult.Idle())
    var dataBooking = MutableLiveData<NetworkResult<Booking>>(NetworkResult.Idle())

    fun getAllHotel(){
        allHotelResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            allHotelResponse.value = homeRepository.getAllHotel()
        }
    }

    fun getAllKota(){
        allKotaResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            allKotaResponse.value = homeRepository.getAllKota()
        }
    }

    fun getHotelbyLokasi(lokasi: Int){
        allHotelResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            allHotelResponse.value = homeRepository.getHotelbyLokasi(lokasi)
        }
    }

    fun getDetailById(id: Int){
        detailHotel.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            detailHotel.value = homeRepository.getDetailById(id)
        }
    }

    fun postBooking(
        ref_hotel: Int,
        start: String,
        end: String,
        durasi: Long,
        total: Long,
        token: String
    ){
        dataBooking.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            dataBooking.value = homeRepository.postBooking(ref_hotel, start, end, durasi, total, token)
        }
    }

}