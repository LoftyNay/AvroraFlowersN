package com.ltn.avroraflowers.dagger.module

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.ltn.avroraflowers.utils.FragmentManagerUtils
import com.ltn.avroraflowers.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideUtils(context: Context) : Utils {
        return Utils(context)
    }
}