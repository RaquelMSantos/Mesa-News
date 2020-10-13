package br.com.rmso.mesanews.login

import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.repository.LoginUseCase

class LoginViewModel (val loginUseCase: LoginUseCase)
    : ViewModel() {

    fun fetchLogin(loginRequest: LoginRequest){
        loginUseCase.executeSignin(loginRequest)
    }
}