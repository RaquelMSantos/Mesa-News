package br.com.rmso.mesanews.network.request

data class RegisterRequest (
    var name: String,
    var email: String,
    var password: String
)