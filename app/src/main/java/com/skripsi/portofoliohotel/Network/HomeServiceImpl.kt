package com.skripsi.portofoliohotel.Network

import android.util.Log
import com.skripsi.portofoliohotel.Model.Constant
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.allhotel.AllhotelResponse
import com.skripsi.portofoliohotel.Model.booking.Booking
import com.skripsi.portofoliohotel.Model.detail.DetailHotel
import com.skripsi.portofoliohotel.Model.kota.AllKota
import com.skripsi.portofoliohotel.Model.register.ResponseRegister
import com.skripsi.portofoliohotel.ReqBook.ReqBooking
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class HomeServiceImpl(private val client: HttpClient): HomeService {
    override suspend fun getAllHotel(): NetworkResult<AllhotelResponse> {
        return try{
            val response: AllhotelResponse = client.get {
                url(Constant.ALL_HOTEL_URL)
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun getHotelbyLokasi(lokasi: Int): NetworkResult<AllhotelResponse> {
        return try{
            val response: AllhotelResponse = client.get {
                url(Constant.KOTA_URL+"/$lokasi")
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun getKota(): NetworkResult<AllKota> {
        return try{
            val response: AllKota = client.get {
                url(Constant.KOTA_URL)
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            Log.d("error", e.message.toString())
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun getDetailById(id: Int): NetworkResult<DetailHotel> {
        return try{
            val response: DetailHotel = client.get {
                url(Constant.ALL_HOTEL_URL+"/$id")
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun postBooking(
        ref_hotel: Int,
        start: String,
        end: String,
        durasi: Long,
        total: Long,
        token: String
    ): NetworkResult<Booking> {
        val book = ReqBooking(ref_hotel, start, end, durasi, total, token)
        val json = Json.encodeToJsonElement(ReqBooking.serializer(), book)
        return try{
            val response: Booking = client.post{
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                url(Constant.BOOKING_URL)
                contentType(ContentType.Application.Json)
                body=json
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            Log.d("Error", e.message.toString())
            NetworkResult.Error(e.message)
        }
    }
}