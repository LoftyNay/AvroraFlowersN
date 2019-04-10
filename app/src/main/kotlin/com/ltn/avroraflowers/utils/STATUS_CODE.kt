package com.ltn.avroraflowers.utils

enum class STATUS_CODE(val value: Int) {
    USER_NOT_FOUND(401),
    WRONG_PASSWORD(403),
    USER_EXIST(409),
    OK(200),
    CREATED(201)
}