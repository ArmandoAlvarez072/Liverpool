package com.armandoalvarez.liverpool.data.util

data class Sorted(
    var name: String,
    var value: Int,
){
    override fun toString(): String {
        return name
    }
}