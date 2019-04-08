package com.ltn.avroraflowers.dagger

import com.ltn.avroraflowers.dagger.module.ContextModule
import com.ltn.avroraflowers.dagger.module.InteractorsModule
import com.ltn.avroraflowers.dagger.module.NetworkModule
import com.ltn.avroraflowers.dagger.module.UtilsModule
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.EntryLoginFragment
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.EntryLoginInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter.EntryLoginFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(ContextModule::class, UtilsModule::class, NetworkModule::class, InteractorsModule::class))
@Singleton
interface AppComponent {
    fun inject(entryActivity: EntryActivity)
    fun inject(entryLoginFragment: EntryLoginFragment)

    //IN PRESENTER
    fun inject(entryLoginFragmentPresenter: EntryLoginFragmentPresenter)

    //IN INTERACTOR
    fun inject(baseInteractor: BaseInteractor)
}