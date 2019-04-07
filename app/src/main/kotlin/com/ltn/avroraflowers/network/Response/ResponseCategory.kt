package com.ltn.avroraflowers.network.Response

import com.google.gson.annotations.SerializedName
import com.ltn.avroraflowers.model.Category

data class ResponseCategory (
    val count: Int,
    @SerializedName("result")
    val listCategories: List<Category>
)