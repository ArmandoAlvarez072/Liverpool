package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.presentation.adapter.ProductsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }
}