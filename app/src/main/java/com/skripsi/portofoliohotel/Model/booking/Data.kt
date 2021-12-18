package com.skripsi.portofoliohotel.Model.booking

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String,
    val durasi: String,
    val end: String,
    val id: Int,
    val ref_hotel: String,
    val ref_user: Int,
    val start: String,
    val total: String,
    val updated_at: String
)