package br.com.rmso.mesanews.repository.remote.login.register

import br.com.rmso.mesanews.network.request.RegisterRequest

class RegisterUseCase(private val registerRepository: RegisterRepository) {
    fun executeSignup(registerRequest: RegisterRequest) {
        return this.registerRepository.signup(registerRequest)
    }
}