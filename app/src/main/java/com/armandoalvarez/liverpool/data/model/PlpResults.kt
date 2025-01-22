package com.armandoalvarez.liverpool.data.model


import com.google.gson.annotations.SerializedName

data class PlpResults(
    @SerializedName("plpState")
    var plpState: PlpState?,
    @SerializedName("records")
    var records: List<Record?>?
)