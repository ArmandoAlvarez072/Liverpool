package com.armandoalvarez.liverpool.data.repository.datasource

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import retrofit2.Response

interface LiverpoolRemoteDataSource {

    suspend fun searchProduct(
        product: String,
        page: Int
    ) : Response<SearchProductResponse>

    suspend fun searchProductSorted(
        product: String,
        page: Int,
        sorted: Int
    ) : Response<SearchProductResponse>

}