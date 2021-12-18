package com.skripsi.portofoliohotel.Network

import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.login.ResponseLogin
import com.skripsi.portofoliohotel.Model.register.ResponseRegister

interface AuthService {
    suspend fun postLogin(email: String, password: String): NetworkResult<ResponseLogin>
    suspend fun postRegister(email: String, password: String, name: String): NetworkResult<ResponseRegister>
}