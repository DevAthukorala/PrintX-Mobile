package com.example.dewminiproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val productType: String,
    val selectedSize: String,
    val selectedMaterial: String,
    val quantity: Int,
    val totalPrice: Double,
    val deliveryMethod: String,
    val deliveryAddress: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis(),
    val scheduledDate: Long = 0, // For rescheduling
    val customInstructions: String = "",
    val designFileUri: String = ""
)
