package com.skripsi.portofoliohotel.Model.allhotel

import kotlinx.serialization.Serializable

@Serializable
data class AllhotelResponse(
    val `data`: List<Data>
)