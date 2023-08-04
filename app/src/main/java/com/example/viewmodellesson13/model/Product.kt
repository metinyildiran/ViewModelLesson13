package com.example.viewmodellesson13.model

import java.lang.Exception
import java.util.UUID

sealed class ProductListState {
    object Idle : ProductListState()
    object Loading : ProductListState()
    class Result(val products: List<Product>) : ProductListState()
    class Error(val exception: Exception) : ProductListState()
}

data class Product(
    val name: String,
    val imageUrl: String,
    var isFavourite: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
