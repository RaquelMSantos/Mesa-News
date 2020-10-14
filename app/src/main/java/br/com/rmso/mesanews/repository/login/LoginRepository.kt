package br.com.rmso.mesanews.repository.login

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.AuthResponse

interface LoginRepository {
    fun signin(loginRequest: LoginRequest)
}