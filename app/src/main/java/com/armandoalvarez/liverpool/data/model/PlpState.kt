package com.armandoalvarez.liverpool.data.model


import com.google.gson.annotations.SerializedName

data class PlpState(
    @SerializedName("categoryId")
    var categoryId: String?,
    @SerializedName("currentFilters")
    var currentFilters: String?,
    @SerializedName("currentSortOption")
    var currentSortOption: String?,
    @SerializedName("firstRecNum")
    var firstRecNum: Int?,
    @SerializedName("lastRecNum")
    var lastRecNum: Int?,
    @SerializedName("originalSearchTerm")
    var originalSearchTerm: String?,
    @SerializedName("plpSellerName")
    var plpSellerName: String?,
    @SerializedName("recsPerPage")
    var recsPerPage: Int?,
    @SerializedName("totalNumRecs")
    var totalNumRecs: Int?
)