package br.com.rmso.mesanews.network

import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.network.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface NewsApi {
    @POST(Constants.SIGNIN_URL)
    @FormUrlEncoded
    fun signin(@Body request: LoginRequest): Call<LoginResponse>

//    fun feed(@Header("Authorization") token: String): Call<FeedResponse>
}