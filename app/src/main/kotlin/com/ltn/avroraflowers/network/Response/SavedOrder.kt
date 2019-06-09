package com.ltn.avroraflowers.network.Response

data class SavedOrder(
    var save_order_id: Int,
    var name: String,
    var title: String,
    var per_pack: Int,
    var count_pack: Int
)