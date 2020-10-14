package br.com.rmso.mesanews.ui.register

import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.repository.remote.login.register.RegisterUseCase

class RegisterViewModel (private val registerUseCase: RegisterUseCase)
    : ViewModel() {

    fun fetchRegister(registerRequest: RegisterRequest){
        registerUseCase.executeSignup(registerRequest)
    }
}