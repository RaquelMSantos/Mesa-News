package br.com.rmso.mesanews.repository.remote.login

import br.com.rmso.mesanews.network.request.LoginRequest

class LoginUseCase(private val loginRepository: LoginRepository) {
    fun executeSignin(loginRequest: LoginRequest) {
        return this.loginRepository.signin(loginRequest)
    }
}