package br.com.rmso.mesanews.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.LiveDataResult
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.LoginRequest
import br.com.rmso.mesanews.ui.register.RegisterActivity
import br.com.rmso.mesanews.repository.remote.login.LoginDataSource
import br.com.rmso.mesanews.repository.remote.login.LoginUseCase
import br.com.rmso.mesanews.ui.MainActivity
import com.facebook.login.widget.LoginButton
import com.facebook.FacebookSdk;
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.coroutines.Dispatchers

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

        initViewModel()
        observerViewModel()
    }

    private fun fieldValidation() {
        val required = "Required"
        when {
            et_email.text.toString().trim().isEmpty() -> {
                et_email.error = required
            }
            et_password.text.toString().trim().isEmpty() -> {
                et_password.error = required
            }
            else -> {
                fetchLogin(et_email.text.toString(), et_password.text.toString())
            }
        }
    }

    private fun fetchLogin(email: String, password: String){
        viewModel.fetchLogin(LoginRequest(email = email, password = password), AuthManager(context = this))
    }

    private fun initViewModel() {
        val viewModelFactory = LoginViewModelFactory(
            Dispatchers.Main,
            Dispatchers.IO,
            LoginUseCase(
                LoginDataSource(
                    ApiService.createService(NewsApi::class.java)
                )
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

    }

    private fun observerViewModel() {
        viewModel.loginLiveData.observe(this, Observer<LiveDataResult<String>>{
            when (it?.status) {
                LiveDataResult.STATUS.ERROR -> {
                    statusProgressBar(false)
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

                LiveDataResult.STATUS.SUCCESS -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    statusProgressBar(false)
                }

                LiveDataResult.STATUS.LOADING -> {
                    statusProgressBar(true)
                }
            }
        })
    }

    private fun statusProgressBar(status: Boolean) {
        if(status) {
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}