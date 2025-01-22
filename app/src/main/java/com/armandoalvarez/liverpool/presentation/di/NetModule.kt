package com.armandoalvarez.liverpool.presentation.di

import com.armandoalvarez.liverpool.BuildConfig
import com.armandoalvarez.liverpool.data.api.LiverpoolApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().serializeNulls()
            .setLenient().create()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor {
            var request: Request = it.request()
            val builder = request.newBuilder()
            request = builder.build()
            it.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)

        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesLiverpoolApiService(retrofit: Retrofit): LiverpoolApiService {
        return retrofit.create(LiverpoolApiService::class.java)
    }

}