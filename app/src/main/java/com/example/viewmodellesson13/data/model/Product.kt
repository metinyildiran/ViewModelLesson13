package com.example.viewmodellesson13.data.model

import java.util.UUID

data class Product(
    val name: String,
    val imageUrl: String,
    var isFavourite: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
