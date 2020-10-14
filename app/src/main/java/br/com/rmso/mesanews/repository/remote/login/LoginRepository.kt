package br.com.rmso.mesanews.repository.remote.login

import br.com.rmso.mesanews.network.request.LoginRequest

interface LoginRepository {
    fun signin(loginRequest: LoginRequest)
}