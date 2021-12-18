package com.skripsi.portofoliohotel.ReqBook

import kotlinx.serialization.Serializable

@Serializable
data class ReqBooking(
    val ref_hotel: Int,
    val start: String,
    val end: String,
    val durasi: Long,
    val total: Long,
    val token: String
)
