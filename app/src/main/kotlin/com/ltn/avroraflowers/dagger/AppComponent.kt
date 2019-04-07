package com.ltn.avroraflowers.dagger

import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.dagger.module.ContextModule
import com.ltn.avroraflowers.dagger.module.NetworkModule
import com.ltn.avroraflowers.ui.activities.EntryActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(ContextModule::class, NetworkModule::class))
@Singleton
interface AppComponent {
    fun inject(entryActivity: EntryActivity)
}