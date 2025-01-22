package com.armandoalvarez.liverpool.domain.usecase

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.util.Resource
import com.armandoalvarez.liverpool.domain.repository.LiverpoolRepository

class SearchProductSortedUseCase(
    private val repository: LiverpoolRepository
) {

    suspend fun execute(product: String, page: Int, sorted: Int): Resource<SearchProductResponse> {
        return repository.searchProductSorted(product, page, sorted)
    }

}