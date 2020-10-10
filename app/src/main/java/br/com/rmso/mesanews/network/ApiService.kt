package br.com.rmso.mesanews.network

import br.com.rmso.mesanews.Constants
import br.com.rmso.mesanews.login.LoginRequest
import br.com.rmso.mesanews.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.SIGNIN_URL)
    @FormUrlEncoded
    fun signin(@Body request: LoginRequest): Call<LoginResponse>
}