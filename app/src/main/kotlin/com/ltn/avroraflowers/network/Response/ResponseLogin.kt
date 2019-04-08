package com.ltn.avroraflowers.network.Response

data class ResponseLogin (
    val code: Int,
    val status: String,
    var token: String
)