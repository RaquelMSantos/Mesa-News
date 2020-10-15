package br.com.rmso.mesanews.repository.remote.login.register

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.RegisterRequest

interface RegisterRepository {
    suspend fun signup(
        registerRequest: RegisterRequest,
        authManager: AuthManager
    ): String
}