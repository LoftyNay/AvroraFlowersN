package com.ltn.avroraflowers.dagger.module

import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.RegisterActivityInteractor
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.CatalogFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.EntryLoginInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Provides
    @Singleton
    fun provideEntryLoginInteractor(): EntryLoginInteractor {
        return EntryLoginInteractor()
    }

    @Provides
    @Singleton
    fun provideRegisterActivityInteractor(): RegisterActivityInteractor {
        return RegisterActivityInteractor()
    }

    @Provides
    @Singleton
    fun provideCatalogFragmentInteractor(): CatalogFragmentInteractor {
        return CatalogFragmentInteractor()
    }
}