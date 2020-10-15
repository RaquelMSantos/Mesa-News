package br.com.rmso.mesanews.repository.remote.login

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.LoginRequest

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun executeSignin(
        loginRequest: LoginRequest,
        authManager: AuthManager
    ): String {
        return this.loginRepository.signin(loginRequest, authManager)
    }

}