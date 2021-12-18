package com.skripsi.portofoliohotel.Model.detail

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val alamat: String,
    val biaya: Int,
    val created_at: String,
    val fasilitas: String,
    val foto: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val ref_kota: Int,
    val updated_at: String
)