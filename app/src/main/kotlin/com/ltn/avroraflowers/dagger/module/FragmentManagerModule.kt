package com.ltn.avroraflowers.dagger.module

import androidx.fragment.app.FragmentManager
import com.ltn.avroraflowers.utils.FragmentManagerUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentManagerModule(private val fragmentManager: FragmentManager) {

    @Provides
    @Singleton
    fun provideFragmentManagerUtils(): FragmentManagerUtils {
        return FragmentManagerUtils(fragmentManager)
    }
}