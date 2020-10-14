package br.com.rmso.mesanews.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rmso.mesanews.repository.register.RegisterUseCase
import java.lang.IllegalArgumentException

class RegisterViewModelFactory
    constructor(private val registerUseCase: RegisterUseCase)
    :ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(RegisterViewModel::class.java) ->
                        RegisterViewModel(registerUseCase)
                    else ->
                        throw IllegalArgumentException("ViewModel Not Found")
                }
            } as T
}