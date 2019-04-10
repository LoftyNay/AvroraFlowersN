package com.ltn.avroraflowers

import android.app.Application
import com.ltn.avroraflowers.dagger.ApplicationComponent
import com.ltn.avroraflowers.dagger.DaggerApplicationComponent
import com.ltn.avroraflowers.dagger.module.ContextModule

class App : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    fun getComponent(): ApplicationComponent {
        return component
    }

    override fun onCreate() {
        super.onCreate()
        component = buildDaggerComponent()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun buildDaggerComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }
}