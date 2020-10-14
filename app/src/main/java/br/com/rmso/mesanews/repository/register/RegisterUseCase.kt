package br.com.rmso.mesanews.repository.register

import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.network.response.AuthResponse
import br.com.rmso.mesanews.repository.register.RegisterRepository

class RegisterUseCase(private val registerRepository: RegisterRepository) {
    fun executeSignup(registerRequest: RegisterRequest) {
        return this.registerRepository.signup(registerRequest)
    }
}