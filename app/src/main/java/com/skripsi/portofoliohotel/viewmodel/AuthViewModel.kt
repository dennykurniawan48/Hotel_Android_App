package com.skripsi.portofoliohotel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.portofoliohotel.Model.NetworkResult
import com.skripsi.portofoliohotel.Model.login.ResponseLogin
import com.skripsi.portofoliohotel.Model.register.ResponseRegister
import com.skripsi.portofoliohotel.repository.AuthRepository
import com.skripsi.portofoliohotel.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository, private val datastoreRepository: DatastoreRepository):ViewModel() {
    var loginResponse = MutableLiveData<NetworkResult<ResponseLogin>>(NetworkResult.Idle())
    var registerResponse = MutableLiveData<NetworkResult<ResponseRegister>>(NetworkResult.Idle())
    var token = datastoreRepository.flowToken
    var onBoardFinish = datastoreRepository.flowOnBoard

    fun postLogin(email: String, password: String){
        loginResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            val result = repository.postLogin(email, password)
            if(result is NetworkResult.Success){
                datastoreRepository.saveToken(result.data!!.data.token)
            }
            loginResponse.value = result
        }
    }

    fun postRegister(email: String, password: String, name: String){
        loginResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            val result = repository.postRegister(email, password, name)
            registerResponse.value = result
        }
    }

    fun onBoardFinished(){
        viewModelScope.launch {
            datastoreRepository.saveOnBoard()
        }
    }
}