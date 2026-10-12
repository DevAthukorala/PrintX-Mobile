package com.example.dewminiproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val basePrice: Double,
    val category: String,
    val availableSizes: String, // Comma-separated: "A4, A3, 2x3ft"
    val availableMaterials: String, // Comma-separated: "Matte, Glossy, Vinyl"
    val imageRes: Int = 0
)
