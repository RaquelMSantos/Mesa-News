package br.com.rmso.mesanews.repository

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.LoginResponse

class LoginUseCase(private val loginRepository: LoginRepository) {
    fun executeSignin(loginRequest: LoginRequest): LoginResponse {
        return this.loginRepository.signin(loginRequest)
    }
}