package com.ltn.avroraflowers.network.Response

import com.ltn.avroraflowers.model.Product

data class SearchResponse(
    val limit: Int,
    val page: Int,
    val result: List<Product>
)