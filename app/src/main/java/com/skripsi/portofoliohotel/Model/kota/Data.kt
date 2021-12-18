package com.skripsi.portofoliohotel.Model.kota

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String?,
    val id: Int,
    val nama: String,
    val updated_at: String?
)