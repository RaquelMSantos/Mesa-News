package br.com.rmso.mesanews.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rmso.mesanews.AuthManager
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiClient
import com.facebook.login.widget.LoginButton
import com.facebook.FacebookSdk;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager
    private lateinit var apiClient: ApiClient
    private lateinit var btnFacebook: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)

        btnFacebook = findViewById(R.id.btn_facebook)

        apiClient = ApiClient()
        authManager = AuthManager(this)

        apiClient.getApiService().signin(LoginRequest(email = "", password = ""))
            .enqueue(object: Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()


                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

    }
}