package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.data.api.LiverpoolApiService
import com.armandoalvarez.liverpool.data.repository.datasource.LiverpoolRemoteDataSource
import com.armandoalvarez.liverpool.data.repository.datasourceimpl.LiverpoolRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesLiverpoolRemoteDataSource(
        apiService: LiverpoolApiService
    ): LiverpoolRemoteDataSource {
        return LiverpoolRemoteDataSourceImpl(apiService)
    }

}