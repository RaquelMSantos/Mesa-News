package br.com.rmso.mesanews.repository

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.LoginResponse
import com.facebook.login.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

class LoginDataSource(private val newsApi: NewsApi): LoginRepository {

    private lateinit var authManager: AuthManager

    override fun signin(loginRequest: LoginRequest): LoginResponse {
        val call = this.newsApi.signin(loginRequest)
        lateinit var loginResponse: LoginResponse
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginResponse = response.body()!!
                if (response.isSuccessful) {
                    authManager.saveAuthToken(loginResponse.token)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })
        return loginResponse
    }
}