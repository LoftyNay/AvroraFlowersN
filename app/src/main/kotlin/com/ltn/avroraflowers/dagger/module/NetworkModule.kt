package com.ltn.avroraflowers.dagger.module

import com.google.gson.Gson
import com.ltn.avroraflowers.network.ApiAvrora
import com.ltn.avroraflowers.utils.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        return retrofit
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiAvrora {
        return retrofit.create(ApiAvrora::class.java)
    }
}