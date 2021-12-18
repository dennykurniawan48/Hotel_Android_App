package com.skripsi.portofoliohotel.Model

import androidx.annotation.DrawableRes
import com.skripsi.portofoliohotel.R

sealed class OnboardData(
    @DrawableRes
    val image: Int,
    val title: String,
    val desc: String
){
    object Board1: OnboardData(R.drawable.onboard1, "Hotel Mudah", "Bisa mencari hotel berdasarkan lokasi")
    object Board2: OnboardData(R.drawable.onboard2, "Hotel Murah", "Cari hotel sesuai dengan budget yang anda miliki")
    object Board3: OnboardData(R.drawable.onboard3, "Proses Cepat", "Tinggal klik booking, dan lakukan pembayaran di hotel tujuan")
}
