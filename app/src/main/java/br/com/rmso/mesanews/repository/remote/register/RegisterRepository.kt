package br.com.rmso.mesanews.repository.remote.login.register

import br.com.rmso.mesanews.network.request.RegisterRequest

interface RegisterRepository {
    fun signup(registerRequest: RegisterRequest)
}