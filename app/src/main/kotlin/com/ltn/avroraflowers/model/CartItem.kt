package com.ltn.avroraflowers.model

import com.google.gson.annotations.Expose

data class CartItem(
    var _id: Int,
    var user_id: Int,
    var product_id: Int,
    var title: String,
    var per_pack: Int,
    var count_pack: Int,
    var color: String,
    var price: String,
    @Expose
    var checked: Boolean = false
)
