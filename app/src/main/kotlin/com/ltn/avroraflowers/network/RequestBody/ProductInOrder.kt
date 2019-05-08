package com.ltn.avroraflowers.network.RequestBody

data class ProductInOrder(
    var count_per_pack: Int,
    var per_pack: Int,
    var price: String,
    var product_id: Int
)