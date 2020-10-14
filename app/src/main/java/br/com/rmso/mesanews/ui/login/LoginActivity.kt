package br.com.rmso.mesanews.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.ui.register.RegisterActivity
import br.com.rmso.mesanews.repository.login.LoginDataSource
import br.com.rmso.mesanews.repository.login.LoginUseCase
import com.facebook.login.widget.LoginButton
import com.facebook.FacebookSdk;
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var btnFacebook: LoginButton
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)

        btnFacebook = findViewById(R.id.btn_facebook)

        btn_signin.setOnClickListener {
            fieldValidation()
        }

        tv_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fieldValidation() {
        val required = "Required"
        when {
            et_name.text.toString().trim().isEmpty() -> {
                et_name.error = required
            }
            et_email.text.toString().trim().isEmpty() -> {
                et_email.error = required
            }
            else -> {
                initViewModel(et_email.toString(), et_password.toString())
            }
        }
    }

    private fun initViewModel(email: String, password: String) {
        val viewModelFactory = LoginViewModelFactory(
            LoginUseCase(
                LoginDataSource(
                    ApiService.createService(NewsApi::class.java)
                )
            )
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        viewModel.fetchLogin(LoginRequest(email = email, password = password))
    }
}