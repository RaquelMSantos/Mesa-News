package br.com.rmso.mesanews.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.network.request.RegisterRequest
import br.com.rmso.mesanews.repository.register.RegisterDataSource
import br.com.rmso.mesanews.repository.register.RegisterUseCase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            fieldValidation()
        }
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
                Toast.makeText(this, "Confirm!", Toast.LENGTH_LONG).show()
                initViewModel(
                    et_name.text.toString(),
                    et_email.text.toString(),
                    et_password.text.toString()
                )
            }
        }
    }

    private fun initViewModel(
        name: String,
        email: String,
        password: String
    ) {
        val viewModelFactory = RegisterViewModelFactory(
            RegisterUseCase(
                RegisterDataSource(
                    ApiService.createService(NewsApi::class.java)
                )
            )
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RegisterViewModel::class.java)
        viewModel.fetchRegister(RegisterRequest(name = name, email = email, password = password))
    }
}