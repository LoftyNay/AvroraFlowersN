package com.ltn.avroraflowers.dagger.module

import android.content.Context
import com.ltn.avroraflowers.utils.FieldsValidator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FieldsValidatorModule {

    @Singleton
    @Provides
    fun provideFieldsValidator(): FieldsValidator {
        return FieldsValidator()
    }
}