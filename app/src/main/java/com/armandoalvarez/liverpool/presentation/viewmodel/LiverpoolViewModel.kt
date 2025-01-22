package com.armandoalvarez.liverpool.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.armandoalvarez.liverpool.data.model.SearchProductResponse
import com.armandoalvarez.liverpool.data.util.DispatcherIO
import com.armandoalvarez.liverpool.data.util.Network
import com.armandoalvarez.liverpool.data.util.Resource
import com.armandoalvarez.liverpool.domain.usecase.SearchProductSortedUseCase
import com.armandoalvarez.liverpool.domain.usecase.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiverpoolViewModel @Inject constructor(
    private val application: Application,
    private val searchProductUseCase: SearchProductUseCase,
    private val searchProductSortedUseCase: SearchProductSortedUseCase,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var productsResponse: MutableLiveData<Resource<SearchProductResponse>> = MutableLiveData()

    fun searchProduct(product: String, page: Int) = viewModelScope.launch(dispatcher) {
        productsResponse.postValue(Resource.Loading())
        try {
            if (Network.isNetworkAvailable(application)) {
                val apiResult = searchProductUseCase.execute(product, page)
                productsResponse.postValue(apiResult)
            } else {
                productsResponse.postValue(Resource.Error("No hay encontró conexión a internet"))
            }
        } catch (e: Exception) {
            productsResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun searchProductSorted(product: String, page: Int, sorted: Int) = viewModelScope.launch(dispatcher) {

        productsResponse.postValue(Resource.Loading())
        try {
            if (Network.isNetworkAvailable(application)) {
                val apiResult = searchProductSortedUseCase.execute(product, page, sorted)
                productsResponse.postValue(apiResult)
            } else {
                productsResponse.postValue(Resource.Error("No hay encontró conexión a internet"))
            }
        } catch (e: Exception) {
            productsResponse.postValue(Resource.Error(e.message.toString()))
        }
    }


}