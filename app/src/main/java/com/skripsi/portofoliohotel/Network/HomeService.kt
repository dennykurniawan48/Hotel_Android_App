package com.skripsi.portofoliohotel.Network

import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.allhotel.AllhotelResponse
import com.skripsi.portofoliohotel.Model.booking.Booking
import com.skripsi.portofoliohotel.Model.detail.DetailHotel
import com.skripsi.portofoliohotel.Model.kota.AllKota

interface HomeService {
    suspend fun getAllHotel(): NetworkResult<AllhotelResponse>
    suspend fun getHotelbyLokasi(lokasi: Int): NetworkResult<AllhotelResponse>
    suspend fun getKota(): NetworkResult<AllKota>
    suspend fun getDetailById(id: Int): NetworkResult<DetailHotel>
    suspend fun postBooking(ref_hotel: Int, start: String, end: String, durasi: Long, total: Long, token: String): NetworkResult<Booking>
}