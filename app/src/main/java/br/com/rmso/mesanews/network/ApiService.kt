package br.com.rmso.mesanews.network

import android.content.Context
import br.com.rmso.mesanews.auth.AuthInterceptor
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private fun getRetrofit(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    fun <S> createService(context: Context, serviceClass: Class<S>): S {
        return getRetrofit(context).create(serviceClass)
    }
}