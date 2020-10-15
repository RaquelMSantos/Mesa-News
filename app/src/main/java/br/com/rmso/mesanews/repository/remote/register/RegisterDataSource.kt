package br.com.rmso.mesanews.repository.remote.login.register

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.RegisterRequest

class RegisterDataSource(private val newsApi: NewsApi): RegisterRepository {

    override suspend fun signup(registerRequest: RegisterRequest, authManager: AuthManager) =
        this.newsApi.signup(registerRequest).execute().body()?.token
            ?: throw NullPointerException("No body")
}