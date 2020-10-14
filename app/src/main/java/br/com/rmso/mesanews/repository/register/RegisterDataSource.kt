package br.com.rmso.mesanews.repository.register

import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.network.response.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterDataSource(private val newsApi: NewsApi):
    RegisterRepository {

    private lateinit var authManager: AuthManager

    override fun signup(registerRequest: RegisterRequest) {
        val call = this.newsApi.signup(registerRequest)
        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        authManager.saveAuthToken(loginResponse.token)
                    }
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

            }
        })
    }
}