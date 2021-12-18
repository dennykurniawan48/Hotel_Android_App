package com.skripsi.portofoliohotel.Model.login

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String,
    val email: String,
    val email_verified_at: String? = null,
    val id: Int,
    val name: String,
    val token: String,
    val updated_at: String
)