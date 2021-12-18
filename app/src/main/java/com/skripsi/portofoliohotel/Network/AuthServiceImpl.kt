package com.skripsi.portofoliohotel.Network

import com.skripsi.portofoliohotel.Model.Constant
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.login.ResponseLogin
import com.skripsi.portofoliohotel.Model.register.ResponseRegister
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class AuthServiceImpl(val client: HttpClient): AuthService {
    override suspend fun postLogin(email: String, password: String): NetworkResult<ResponseLogin> {
        return try{
            val response: ResponseLogin = client.submitForm(
                url = Constant.LOGIN_URL,
                formParameters = Parameters.build {
                    append("email", email)
                    append("password", password)
                },
                encodeInQuery = false // if set to true then request become GET Request
            )
            NetworkResult.Success(response)
        }catch (e: ClientRequestException){
            NetworkResult.Error("Username / Password Salah")
        }catch (e: Exception){
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun postRegister(
        email: String,
        password: String,
        name: String
    ): NetworkResult<ResponseRegister> {
        return try{
            val response: ResponseRegister = client.submitForm(
                url = Constant.REGISTER_URL,
                formParameters = Parameters.build {
                    append("email", email)
                    append("password", password)
                    append("name", name)
                },
                encodeInQuery = false // if set to true then request become GET Request
            )
            NetworkResult.Success(response)
        }catch (e: ClientRequestException){
            NetworkResult.Error("Email sudah terdaftar")
        }catch (e: Exception){
            NetworkResult.Error(e.message)
        }
    }
}