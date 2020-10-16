package br.com.rmso.mesanews.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.utils.LiveDataResult
import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.repository.remote.login.LoginUseCase
import kotlinx.coroutines.*
import java.lang.Exception

class LoginViewModel (mainDispacher: CoroutineDispatcher,
                      ioDispatcher: CoroutineDispatcher,
                      private val loginUseCase: LoginUseCase)
    : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispacher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    val loginLiveData: MutableLiveData<LiveDataResult<String>> by lazy {
        MutableLiveData<LiveDataResult<String>>()
    }

    fun fetchLogin(
        loginRequest: LoginRequest,
        authManager: AuthManager
    ){
        uiScope.launch {
            loginLiveData.value = LiveDataResult.loading()
            try {
                val token = executeSignin(loginRequest, authManager)
                authManager.saveAuthToken(token)
                loginLiveData.value = LiveDataResult.success(token)
            }catch (e: Exception){
                loginLiveData.value = LiveDataResult.error(e)
            }
        }
    }

    private suspend fun executeSignin(
        loginRequest: LoginRequest,
        authManager: AuthManager
    ): String {
        return ioScope.async {
            return@async loginUseCase.executeSignin(loginRequest, authManager)
        }.await()
    }
}