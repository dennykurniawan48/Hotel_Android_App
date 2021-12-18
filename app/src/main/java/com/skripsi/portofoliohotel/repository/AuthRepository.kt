package com.skripsi.portofoliohotel.repository

import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.login.ResponseLogin
import com.skripsi.portofoliohotel.Model.register.ResponseRegister
import com.skripsi.portofoliohotel.Network.AuthServiceImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.*
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(private val client: HttpClient) {
    suspend fun postLogin(email: String, password: String): NetworkResult<ResponseLogin>{
        return AuthServiceImpl(client).postLogin(email, password)
    }

    suspend fun postRegister(email: String, password: String, name: String): NetworkResult<ResponseRegister>{
        return AuthServiceImpl(client).postRegister(email, password, name)
    }
}