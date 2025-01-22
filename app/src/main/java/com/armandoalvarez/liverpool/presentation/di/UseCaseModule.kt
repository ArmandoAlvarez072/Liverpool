package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.domain.repository.LiverpoolRepository
import com.armandoalvarez.liverpool.domain.usecase.SearchProductSortedUseCase
import com.armandoalvarez.liverpool.domain.usecase.SearchProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesSearchProductUseCase(
        repository: LiverpoolRepository
    ): SearchProductUseCase {
        return SearchProductUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSearchProductSortedUseCase(
        repository: LiverpoolRepository
    ): SearchProductSortedUseCase {
        return SearchProductSortedUseCase(repository)
    }
}


















