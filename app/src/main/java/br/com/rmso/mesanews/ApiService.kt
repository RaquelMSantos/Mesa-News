package br.com.rmso.mesanews

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.SIGNIN_URL)
    @FormUrlEncoded
    fun signin(@Body request: LoginRequest): Call<LoginResponse>
}