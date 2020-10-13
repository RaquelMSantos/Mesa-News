package br.com.rmso.mesanews.repository

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.LoginResponse

interface LoginRepository {
    fun signin(loginRequest: LoginRequest): LoginResponse
}