package com.armandoalvarez.liverpool.domain.repository

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.util.Resource

interface LiverpoolRepository {

    suspend fun searchProduct(
        product: String,
        page: Int
    ) : Resource<SearchProductResponse>

    suspend fun searchProductSorted(
        product: String,
        page: Int,
        sorted: Int
    ) : Resource<SearchProductResponse>

}