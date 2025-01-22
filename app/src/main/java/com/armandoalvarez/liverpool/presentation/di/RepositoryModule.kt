package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.data.repository.LiverpoolRepositoryImpl
import com.armandoalvarez.liverpool.data.repository.datasource.LiverpoolRemoteDataSource
import com.armandoalvarez.liverpool.domain.repository.LiverpoolRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesLiverpoolRepository(
        remoteDataSource: LiverpoolRemoteDataSource,
    ): LiverpoolRepository {
        return LiverpoolRepositoryImpl(remoteDataSource)
    }

}














