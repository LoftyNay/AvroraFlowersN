package com.ltn.avroraflowers.dagger

import com.ltn.avroraflowers.dagger.module.*
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.activities.registerActivity.RegisterActivity
import com.ltn.avroraflowers.ui.activities.registerActivity.presenter.RegisterActivityPresenter
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.EntryLoginFragment
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter.EntryLoginFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = arrayOf(
        ContextModule::class,
        UtilsModule::class,
        NetworkModule::class,
        InteractorsModule::class,
        FieldsValidatorModule::class
    )
)
@Singleton
interface ApplicationComponent {

    fun inject(entryActivity: EntryActivity)
    fun inject(registerActivity: RegisterActivity)

    fun inject(entryLoginFragment: EntryLoginFragment)

    //IN PRESENTER
    fun inject(entryLoginFragmentPresenter: EntryLoginFragmentPresenter)
    fun inject(registerActivityPresenter: RegisterActivityPresenter)

    //IN INTERACTOR
    fun inject(baseInteractor: BaseInteractor)
}