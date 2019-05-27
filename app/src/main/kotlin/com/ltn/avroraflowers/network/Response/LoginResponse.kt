package com.ltn.avroraflowers.network.Response

data class LoginResponse (
    val name: String,
    val number: String,
    val code: Int,
    val status: String,
    var token: String
)