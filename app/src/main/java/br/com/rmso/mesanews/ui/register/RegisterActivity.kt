package br.com.rmso.mesanews.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.LiveDataResult
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.auth.AuthManager
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.repository.remote.login.register.RegisterDataSource
import br.com.rmso.mesanews.repository.remote.login.register.RegisterUseCase
import br.com.rmso.mesanews.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_email
import kotlinx.android.synthetic.main.activity_register.et_password
import kotlinx.coroutines.Dispatchers

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            fieldValidation()
        }

        initViewModel()
        observerViewModel()
    }

    private fun fieldValidation() {
        val required = "Required"
        val passwords = "Different Passwords"
        when {
            et_name.text.toString().trim().isEmpty() -> {
                et_name.error = required
            }
            et_email.text.toString().trim().isEmpty() -> {
                et_email.error = required
            }
            et_password.text.toString().trim().isEmpty() -> {
                et_password.error = required
            }
            et_confirm_password.text.toString().trim().isEmpty() -> {
                et_confirm_password.error = required
            }
            et_password.text.toString().trim() != et_confirm_password.text.trim().toString() -> {
                et_confirm_password.error = passwords
            }
            else -> {
                fetchRegister(et_name.text.toString(), et_email.text.toString(), et_password.text.toString()
                )
            }
        }
    }

    private fun fetchRegister(name: String, email: String, password: String) {
        viewModel.fetchRegister(RegisterRequest(name = name, email = email, password = password), AuthManager(context = this))
    }

    private fun initViewModel() {
        val viewModelFactory = RegisterViewModelFactory(
            Dispatchers.Main,
            Dispatchers.IO,
            RegisterUseCase(
                RegisterDataSource(
                    ApiService.createService(NewsApi::class.java)
                )
            )
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RegisterViewModel::class.java)
    }

    private fun observerViewModel (){
        viewModel.registerLiveData.observe(this, Observer<LiveDataResult<String>>{
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
            progress_bar_register.visibility = View.VISIBLE
        }else{
            progress_bar_register.visibility = View.INVISIBLE
        }
    }

}