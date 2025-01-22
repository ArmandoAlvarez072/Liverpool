package com.armandoalvarez.liverpool.domain.usecase

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.util.Resource
import com.armandoalvarez.liverpool.domain.repository.LiverpoolRepository

class SearchProductUseCase(
    private val repository: LiverpoolRepository
) {

    suspend fun execute(product: String, page: Int) : Resource<SearchProductResponse> {
        return repository.searchProduct(product, page)
    }

}