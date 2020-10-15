package br.com.rmso.mesanews.repository.remote.login

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.LoginRequest

interface LoginRepository {
    suspend fun signin(
        loginRequest: LoginRequest,
        authManager: AuthManager
    ): String
}