package br.com.rmso.mesanews.auth

import android.content.Context
import android.content.SharedPreferences

class AuthManager (context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        const val SHARED_PREFS_KEY = "shared_prefs_key"
        const val AUTH_TOKEN = "auth_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN, null)
    }
}