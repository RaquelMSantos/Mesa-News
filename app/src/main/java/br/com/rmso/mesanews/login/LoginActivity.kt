package br.com.rmso.mesanews.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.repository.LoginDataSource
import br.com.rmso.mesanews.repository.LoginUseCase
import com.facebook.login.widget.LoginButton
import com.facebook.FacebookSdk;

class LoginActivity : AppCompatActivity() {

    private lateinit var btnFacebook: LoginButton
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)

        btnFacebook = findViewById(R.id.btn_facebook)
        initViewModel()
    }

    private fun initViewModel() {
        val viewModelFactory = LoginViewModelFactory(
            LoginUseCase(LoginDataSource(ApiService.createService(this, NewsApi::class.java))))

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        viewModel.fetchLogin(LoginRequest(email = "", password = ""))
    }
}