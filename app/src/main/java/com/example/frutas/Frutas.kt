package com.example.frutas

data class  Fruit(
    val name: String,
    val id: Long,
    val order: String,
    val genus: String,
    val nutritions: Nutritions,
    var imageUrl: String
)


data class Nutritions (
    val calories: Long,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
)

