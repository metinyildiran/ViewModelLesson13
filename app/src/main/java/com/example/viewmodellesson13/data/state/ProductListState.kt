package com.example.viewmodellesson13.data.state

import com.example.viewmodellesson13.data.model.Product
import java.lang.Exception

sealed class ProductListState {
    object Idle : ProductListState()
    object Loading : ProductListState()
    class Result(val products: List<Product>) : ProductListState()
    class Error(val exception: Exception) : ProductListState()
}