package com.ltn.avroraflowers.network

import com.ltn.avroraflowers.network.Response.ResponseCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiAvrora {

    @GET("/categories")
    fun getCategories(
        @Query("order_by") orderBy: String,
        @Query("type") typeSort: String
    ): Observable<ResponseCategory>

    @GET("/categories")
    fun getCategories(): Observable<ResponseCategory>
}