package com.armandoalvarez.liverpool.data.api

import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LiverpoolApiService {

    @GET("plp")
    suspend fun searchProduct(
        @Query("search-string")
        product: String,
        @Query("page-number")
        page: Int
    ) : Response<SearchProductResponse>

    @GET("plp")
    suspend fun searchProductSorted(
        @Query("search-string")
        product: String,
        @Query("page-number")
        page: Int,
        @Query("minSortPrice")
        sorted: Int
    ) : Response<SearchProductResponse>

}