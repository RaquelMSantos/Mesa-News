package br.com.rmso.mesanews.network

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.network.response.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface NewsApi {
    @POST(Constants.SIGNIN_URL)
    fun signin(@Body request: LoginRequest): Call<AuthResponse>

    @POST(Constants.SIGNUP_URL)
    fun signup(@Body request: RegisterRequest): Call<AuthResponse>

//    fun feed(@Header("Authorization") token: String): Call<FeedResponse>
}