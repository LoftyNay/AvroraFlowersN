package com.ltn.avroraflowers.utils

enum class STATUS_CODE(val value: Int) {
    USER_NOT_FOUND(401),
    WRONG_PASSWORD(403),
    OK(200)
}