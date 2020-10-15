package br.com.rmso.mesanews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.auth.AuthManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

        loadToken()
    }

    private fun setupViews() {
        bottom_navigation.setupWithNavController(Navigation.findNavController(this,
            R.id.nav_host_fragment
        ))
    }

    fun loadToken(): String? {
        authManager = AuthManager(this)
        return authManager.getAuthToken()
    }
}
