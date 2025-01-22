package com.armandoalvarez.liverpool.data.repository.datasourceimpl

import com.armandoalvarez.liverpool.data.api.LiverpoolApiService
import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.repository.datasource.LiverpoolRemoteDataSource
import retrofit2.Response

class LiverpoolRemoteDataSourceImpl(
    private val apiService: LiverpoolApiService
) : LiverpoolRemoteDataSource {

    override suspend fun searchProduct(
        product: String,
        page: Int
    ): Response<SearchProductResponse> {
        return apiService.searchProduct(product, page)
    }

    override suspend fun searchProductSorted(
        product: String,
        page: Int,
        sorted: Int
    ): Response<SearchProductResponse> {
        return apiService.searchProductSorted(product, page, sorted)
    }
}