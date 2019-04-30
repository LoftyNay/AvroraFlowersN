package com.ltn.avroraflowers.model

import com.google.gson.annotations.SerializedName

data class Product(
    val _id: Int,
    val category_id: Int,
    val title: String,
    val description: String,
    val image: String,
    val price: String,

    @SerializedName(value = "atributes_per_pack")
    val perPack: Int,

    @SerializedName(value = "atributes_seasonality")
    val seasonality: String,

    @SerializedName(value = "atributes_stem_length")
    val stemLength: String
)