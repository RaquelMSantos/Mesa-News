package br.com.rmso.mesanews.repository.remote.login

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.LoginRequest

class LoginDataSource(private val newsApi: NewsApi):
    LoginRepository {

    override suspend fun signin(loginRequest: LoginRequest, authManager: AuthManager) =
        this.newsApi.signin(loginRequest).execute().body()?.token
            ?: throw NullPointerException("No body")
}