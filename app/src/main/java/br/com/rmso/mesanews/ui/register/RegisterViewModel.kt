package br.com.rmso.mesanews.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.utils.LiveDataResult
import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.repository.remote.login.register.RegisterUseCase
import kotlinx.coroutines.*
import java.lang.Exception

class RegisterViewModel (mainDispacher: CoroutineDispatcher,
                         ioDispatcher: CoroutineDispatcher,
                         private val registerUseCase: RegisterUseCase)
    : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispacher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    val registerLiveData: MutableLiveData<LiveDataResult<String>> by lazy {
        MutableLiveData<LiveDataResult<String>>()
    }

    fun fetchRegister(
        registerRequest: RegisterRequest,
        authManager: AuthManager
    ){
        uiScope.launch {
            registerLiveData.value = LiveDataResult.loading()
            try {
                val token = executeSignup(registerRequest, authManager)
                authManager.saveAuthToken(token)
                registerLiveData.value = LiveDataResult.success(token)
            } catch (e: Exception) {
                registerLiveData.value = LiveDataResult.error(e)
            }
        }
    }

    private suspend fun executeSignup(
        registerRequest: RegisterRequest,
        authManager: AuthManager
    ): String {
        return ioScope.async {
            return@async registerUseCase.executeSignup(registerRequest, authManager)
        }.await()
    }
}