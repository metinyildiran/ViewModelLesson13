package com.example.viewmodellesson13.model

import java.util.UUID

data class User(
    val name: String,
    val surname: String,
    val profileImageUrl: String,
    val id: UUID = UUID.randomUUID()
)
