package com.ltn.avroraflowers.network.Response

data class LoginResponse (
    val code: Int,
    val status: String,
    var token: String
)