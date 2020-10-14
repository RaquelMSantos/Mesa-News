package br.com.rmso.mesanews.repository.login

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.AuthResponse
import br.com.rmso.mesanews.repository.login.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
    fun executeSignin(loginRequest: LoginRequest) {
        return this.loginRepository.signin(loginRequest)
    }
}