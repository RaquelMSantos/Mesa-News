package br.com.rmso.mesanews.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rmso.mesanews.repository.remote.login.LoginUseCase
import java.lang.IllegalArgumentException

class LoginViewModelFactory
    constructor(private val loginUseCase: LoginUseCase)
    :ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(loginUseCase)
                    else ->
                        throw IllegalArgumentException("unknown class")
                }
            } as T
}