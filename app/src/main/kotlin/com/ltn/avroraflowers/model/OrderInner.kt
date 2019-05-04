package com.ltn.avroraflowers.model

data class OrderInner(
    var _id: Int,
    var color: String,
    var date: String,
    var description: String,
    var image: String,
    var price: String,
    var product_id: Int,
    var title: String
)