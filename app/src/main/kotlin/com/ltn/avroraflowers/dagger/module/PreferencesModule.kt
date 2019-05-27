package com.ltn.avroraflowers.dagger.module

import android.content.Context
import android.content.SharedPreferences
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.PreferencesUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PreferencesUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferenceUtils(preferences: SharedPreferences): PreferencesUtils {
        return PreferencesUtils(preferences)
    }
}