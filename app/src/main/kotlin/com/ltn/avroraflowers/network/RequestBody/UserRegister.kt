package com.ltn.avroraflowers.network.RequestBody

data class UserRegister (
    var name: String,
    var email: String,
    var password: String
)