package br.com.rmso.mesanews.ui.login

import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.repository.login.LoginUseCase

class LoginViewModel (private val loginUseCase: LoginUseCase)
    : ViewModel() {

    fun fetchLogin(loginRequest: LoginRequest){
        loginUseCase.executeSignin(loginRequest)
    }
}