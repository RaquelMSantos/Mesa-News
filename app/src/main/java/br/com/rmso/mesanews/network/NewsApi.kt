package br.com.rmso.mesanews.network

import br.com.rmso.mesanews.New
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

    @GET(Constants.NEWS_URL)
    fun getNews(@Header("Authorization") token: String,
                @Query("current_page") currentPage: Int,
                @Query("per_page") perPage: Int): Call<List<New>>

    @GET(Constants.HIGHLIGHTS_URL)
    fun getHighlights(@Header("Authorization") token: String): Call<List<New>>
}