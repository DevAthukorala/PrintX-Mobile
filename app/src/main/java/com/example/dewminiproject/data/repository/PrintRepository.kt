package com.example.dewminiproject.data.repository

import com.example.dewminiproject.data.local.dao.OrderDao
import com.example.dewminiproject.data.local.dao.ProductDao
import com.example.dewminiproject.data.local.dao.UserDao
import com.example.dewminiproject.data.local.entity.Order
import com.example.dewminiproject.data.local.entity.Product
import com.example.dewminiproject.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class PrintRepository(
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val orderDao: OrderDao
) {
    // User operations
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)

    // Product operations
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    fun getProductsByCategory(category: String): Flow<List<Product>> = productDao.getProductsByCategory(category)
    suspend fun getProductById(id: Int) = productDao.getProductById(id)
    suspend fun insertProducts(products: List<Product>) = productDao.insertProducts(products)

    // Order operations
    fun getOrdersForUser(userId: Int): Flow<List<Order>> = orderDao.getOrdersForUser(userId)
    suspend fun insertOrder(order: Order) = orderDao.insertOrder(order)
    suspend fun updateOrder(order: Order) = orderDao.updateOrder(order)
    suspend fun deleteOrder(orderId: Int) = orderDao.deleteOrder(orderId)
}
