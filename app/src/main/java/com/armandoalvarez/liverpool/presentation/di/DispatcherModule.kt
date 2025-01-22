package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.data.util.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @DispatcherIO
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}