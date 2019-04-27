package com.ltn.avroraflowers.dagger

import com.ltn.avroraflowers.dagger.module.*
import com.ltn.avroraflowers.ui.base.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = arrayOf(
        FragmentManagerModule::class
    )
)
@Singleton
interface ActivityComponent {
    fun inject(baseFragment: BaseFragment)
}