package com.armandoalvarez.liverpool.data.model


import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("listPrice")
    var listPrice: Double?,
    @SerializedName("productDisplayName")
    var productDisplayName: String?,
    @SerializedName("productId")
    var productId: String?,
    @SerializedName("promoPrice")
    var promoPrice: Double?,
    @SerializedName("smImage")
    var smImage: String?,
    @SerializedName("variantsColor")
    var variantsColor: List<VariantsColor?>?,
)