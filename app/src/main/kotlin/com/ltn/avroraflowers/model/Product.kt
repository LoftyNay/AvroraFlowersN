package com.ltn.avroraflowers.model

import com.google.gson.annotations.SerializedName

data class Product(
    var _id: Int,
    var category_id: Int,
    var title: String,
    var color: String,
    var description: String,
    var image: String,
    var price: String,

    @SerializedName(value = "atributes_per_pack")
    var perPack: Int,

    @SerializedName(value = "atributes_seasonality")
    var seasonality: String,

    @SerializedName(value = "atributes_stem_length")
    var stemLength: String
)