package com.ltn.avroraflowers.dagger

import com.arellomobile.mvp.MvpView
import com.ltn.avroraflowers.dagger.module.*
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.activities.registerActivity.RegisterActivity
import com.ltn.avroraflowers.ui.activities.registerActivity.presenter.RegisterActivityPresenter
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.catalogFragment.presenter.CatalogFragmentPresenter
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

    //IN PRESENTER
    fun inject(entryLoginFragmentPresenter: EntryLoginFragmentPresenter)
    fun inject(registerActivityPresenter: RegisterActivityPresenter)
    fun inject(catalogFragmentPresenter: CatalogFragmentPresenter)

    //IN INTERACTOR
    fun inject(baseInteractor: BaseInteractor)
}