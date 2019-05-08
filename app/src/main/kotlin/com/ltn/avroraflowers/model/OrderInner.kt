package com.ltn.avroraflowers.model

data class OrderInner(
    var _id: Int,
    var product_id: Int,
    var title: String,
    var color: String,
    var description: String,
    var image: String,
    var per_pack: Int,
    var count_pack: Int,
    var price: String,
    var date: String
)