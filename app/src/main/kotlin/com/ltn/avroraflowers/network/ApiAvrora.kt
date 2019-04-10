package com.ltn.avroraflowers.network

import com.ltn.avroraflowers.model.UserLogin
import com.ltn.avroraflowers.model.UserRegister
import com.ltn.avroraflowers.network.Response.ResponseCategory
import com.ltn.avroraflowers.network.Response.ResponseLogin
import com.ltn.avroraflowers.network.Response.ResponseRegister
import io.reactivex.Observable
import retrofit2.http.*

interface ApiAvrora {

    @GET("/categories")
    fun getCategories(
        @Query("order_by") orderBy: String,
        @Query("type") typeSort: String
    ): Observable<ResponseCategory>

    @GET("/categories")
    fun getCategories(): Observable<ResponseCategory>

    @Headers("Content-type: application/json")
    @POST("/login")
    fun userLogin(@Body userLogin: UserLogin): Observable<ResponseLogin>

    @Headers("Content-type: application/json")
    @POST("/registration")
    fun userRegister(@Body userRegister: UserRegister): Observable<ResponseRegister>
}