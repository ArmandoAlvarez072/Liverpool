package com.armandoalvarez.liverpool.data.model


import com.google.gson.annotations.SerializedName

data class VariantsColor(
    @SerializedName("colorHex")
    var colorHex: String?,
    @SerializedName("skuId")
    var skuId: Any?
)