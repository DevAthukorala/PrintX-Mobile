package com.example.dewminiproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dewminiproject.data.local.AppDatabase
import com.example.dewminiproject.data.local.entity.Order
import com.example.dewminiproject.data.local.entity.Product
import com.example.dewminiproject.data.local.entity.User
import com.example.dewminiproject.data.repository.PrintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PrintRepository
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        val db = AppDatabase.getDatabase(application)
        repository = PrintRepository(db.userDao(), db.productDao(), db.orderDao())
        
        seedProducts()
    }

    private fun seedProducts() {
        viewModelScope.launch {
            val existing = repository.getAllProducts().first()
            if (existing.isEmpty()) {
                val initialProducts = listOf(
                    Product(name = "Business Cards", description = "Professional cards", basePrice = 500.0, category = "Stationery", availableSizes = "Standard (3.5x2)", availableMaterials = "Matte 300gsm, Glossy 300gsm"),
                    Product(name = "A4 Posters", description = "High res posters", basePrice = 150.0, category = "Marketing", availableSizes = "A4, A3", availableMaterials = "Art Board 250gsm, Photo Paper"),
                    Product(name = "Vinyl Banners", description = "Outdoor banners", basePrice = 2500.0, category = "Signage", availableSizes = "2x3ft, 3x6ft, Custom", availableMaterials = "Standard Vinyl, Blockout Vinyl"),
                    Product(name = "Custom Mugs", description = "Ceramic gifts", basePrice = 1200.0, category = "Gifts", availableSizes = "11oz Standard", availableMaterials = "White Ceramic, Magic Mug"),
                    Product(name = "Stickers", description = "Custom labels", basePrice = 50.0, category = "Labels", availableSizes = "2x2 inch, 4x4 inch", availableMaterials = "Paper Sticker, PVC Waterproof"),
                    Product(name = "T-Shirts", description = "Custom apparel", basePrice = 1800.0, category = "Apparel", availableSizes = "S, M, L, XL, XXL", availableMaterials = "100% Cotton, Polyester Mix")
                )
                repository.insertProducts(initialProducts)
            }
        }
    }

    fun login(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null) {
                _currentUser.value = user
            } else {
                // Handle user not found (create a dummy for testing)
                val newUser = User(name = "Guest User", email = email, phone = "0771234567")
                repository.insertUser(newUser)
                _currentUser.value = repository.getUserByEmail(email)
            }
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            repository.insertOrder(order)
        }
    }

    fun cancelOrder(order: Order) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(status = "Cancelled"))
        }
    }

    fun rescheduleOrder(order: Order, newDate: Long) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(scheduledDate = newDate))
        }
    }

    fun getProducts() = repository.getAllProducts()
    
    suspend fun getProductById(id: Int) = repository.getProductById(id)

    fun getOrders() = _currentUser.value?.let { repository.getOrdersForUser(it.id) }
}
