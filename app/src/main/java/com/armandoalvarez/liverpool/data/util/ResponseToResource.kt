package com.armandoalvarez.liverpool.data.util

import com.google.gson.Gson
import retrofit2.Response

object ResponseToResource {

    fun <T> responseToResource(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }

        val error = parseError(response)
        return Resource.Error(error?.message ?: "")

    }

    private fun <T> parseError(response: Response<T>): ErrorResponse? {
        return try {
            val gson = Gson()
            return gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}