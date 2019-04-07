package com.ltn.avroraflowers

import android.app.Application
import com.ltn.avroraflowers.dagger.AppComponent
import com.ltn.avroraflowers.dagger.DaggerAppComponent

class App : Application () {

    companion object {
        lateinit var component: AppComponent
    }

    fun getComponent() : AppComponent {
        return component
    }

    override fun onCreate() {
        super.onCreate()
        component = buildDaggerComponent()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun buildDaggerComponent() : AppComponent {
        return DaggerAppComponent.builder().build()
    }
}