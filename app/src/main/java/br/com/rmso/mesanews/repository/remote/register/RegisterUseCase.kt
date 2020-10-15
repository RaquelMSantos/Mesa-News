package br.com.rmso.mesanews.repository.remote.login.register

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.RegisterRequest

class RegisterUseCase(private val registerRepository: RegisterRepository) {
    suspend fun executeSignup(
        registerRequest: RegisterRequest,
        authManager: AuthManager
    ): String {
        return this.registerRepository.signup(registerRequest, authManager)
    }
}