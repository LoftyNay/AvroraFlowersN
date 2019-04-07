package com.ltn.avroraflowers.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}