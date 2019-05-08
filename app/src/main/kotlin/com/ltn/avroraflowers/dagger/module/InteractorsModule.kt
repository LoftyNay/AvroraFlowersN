package com.ltn.avroraflowers.dagger.module

import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.RegisterActivityInteractor
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.CartFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.CatalogFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.EntryLoginInteractor
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor.InnerOrderFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.presenter.InnerOrderFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor.InnerProductFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor.OrdersFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.ProductsFragmentInteractor
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

    @Provides
    @Singleton
    fun provideProductsFragmentInteractor(): ProductsFragmentInteractor {
        return ProductsFragmentInteractor()
    }

    @Provides
    @Singleton
    fun provideCartFragmentInteractor(): CartFragmentInteractor {
        return CartFragmentInteractor()
    }

    @Provides
    @Singleton
    fun provideOrdersFragmentInteractor(): OrdersFragmentInteractor {
        return OrdersFragmentInteractor()
    }

    @Provides
    @Singleton
    fun provideInnerOrderFragmentInteractor(): InnerOrderFragmentInteractor {
        return InnerOrderFragmentInteractor()
    }

    @Provides
    @Singleton
    fun provideInnerProductFragmentInteractor(): InnerProductFragmentInteractor {
        return InnerProductFragmentInteractor()
    }
}