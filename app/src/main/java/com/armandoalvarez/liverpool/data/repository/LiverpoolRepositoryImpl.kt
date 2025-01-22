package com.armandoalvarez.liverpool.data.repository

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.repository.datasource.LiverpoolRemoteDataSource
import com.armandoalvarez.liverpool.data.util.Resource
import com.armandoalvarez.liverpool.data.util.ResponseToResource
import com.armandoalvarez.liverpool.domain.repository.LiverpoolRepository

class LiverpoolRepositoryImpl(
    private val dataSource: LiverpoolRemoteDataSource
) : LiverpoolRepository {

    override suspend fun searchProduct(
        product: String,
        page: Int
    ): Resource<SearchProductResponse> {
        return ResponseToResource.responseToResource(
            dataSource.searchProduct(product, page)
        )
    }

    override suspend fun searchProductSorted(
        product: String,
        page: Int,
        sorted: Int
    ): Resource<SearchProductResponse> {
        return ResponseToResource.responseToResource(
            dataSource.searchProduct(product, page)
        )
    }

}