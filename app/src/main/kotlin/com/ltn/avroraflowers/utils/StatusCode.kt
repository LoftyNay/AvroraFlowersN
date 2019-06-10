package com.ltn.avroraflowers.utils

enum class StatusCode(val value: Int) {
    USER_NOT_FOUND(401),
    WRONG_PASSWORD(403),
    USER_EXIST(409),
    OK(200),
    NO_CONTENT(204),
    CREATED(201)
}