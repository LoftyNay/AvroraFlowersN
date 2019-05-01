package com.ltn.avroraflowers.network.RequestBody

data class AddToCart(
    var product_id: Int,
    var count_pack: Int,
    var per_pack: Int
)