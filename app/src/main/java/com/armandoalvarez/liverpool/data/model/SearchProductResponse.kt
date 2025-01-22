package com.armandoalvarez.liverpool.data.model


import com.google.gson.annotations.SerializedName

data class SearchProductResponse(
    @SerializedName("plpResults")
    var plpResults: PlpResults?
)