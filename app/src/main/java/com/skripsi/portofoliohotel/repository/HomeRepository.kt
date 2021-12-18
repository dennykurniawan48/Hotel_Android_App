package com.skripsi.portofoliohotel.repository

import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.allhotel.AllhotelResponse
import com.skripsi.portofoliohotel.Model.booking.Booking
import com.skripsi.portofoliohotel.Model.detail.DetailHotel
import com.skripsi.portofoliohotel.Model.kota.AllKota
import com.skripsi.portofoliohotel.Network.AuthServiceImpl
import com.skripsi.portofoliohotel.Network.HomeServiceImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.*
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(private val client: HttpClient) {
    suspend fun getAllHotel(): NetworkResult<AllhotelResponse>{
        return HomeServiceImpl(client).getAllHotel()
    }
    suspend fun getHotelbyLokasi(lokasi: Int): NetworkResult<AllhotelResponse>{
        return HomeServiceImpl(client).getHotelbyLokasi(lokasi)
    }
    suspend fun getAllKota(): NetworkResult<AllKota>{
        return HomeServiceImpl(client).getKota()
    }

    suspend fun getDetailById(id:Int): NetworkResult<DetailHotel>{
        return HomeServiceImpl(client).getDetailById(id)
    }

    suspend fun postBooking(
        ref_hotel: Int,
        start: String,
        end: String,
        durasi: Long,
        total: Long,
        token: String
    ): NetworkResult<Booking>{
        return HomeServiceImpl(client).postBooking(ref_hotel, start, end, durasi, total, token)
    }
}