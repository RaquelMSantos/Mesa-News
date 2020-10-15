package br.com.rmso.mesanews.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rmso.mesanews.repository.remote.login.register.RegisterUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException

class RegisterViewModelFactory constructor(
    private val mainDispather: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val registerUseCase: RegisterUseCase)
    :ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(RegisterViewModel::class.java) ->
                        RegisterViewModel(mainDispather, ioDispatcher, registerUseCase)
                    else ->
                        throw IllegalArgumentException("ViewModel Not Found")
                }
            } as T
}