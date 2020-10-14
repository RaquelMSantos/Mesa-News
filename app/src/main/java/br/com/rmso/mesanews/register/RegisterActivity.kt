package br.com.rmso.mesanews.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.rmso.mesanews.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
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
            }
        }
    }
}