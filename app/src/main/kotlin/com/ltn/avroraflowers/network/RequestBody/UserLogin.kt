package com.ltn.avroraflowers.network.RequestBody

data class UserLogin(
    var email: String,
    var password: String
)