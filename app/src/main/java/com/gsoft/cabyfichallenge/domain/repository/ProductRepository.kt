package com.gsoft.cabyfichallenge.domain.repository

import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.entity.CartItemCount
import com.gsoft.cabyfichallenge.domain.entity.CartItemDB
import com.gsoft.cabyfichallenge.util.Resource
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    suspend fun getFeedContent(): Resource<ProductsResponse>
    suspend fun addProductToCart(cartItemDB: CartItemDB)
    suspend fun deleteProductFromCart(cartItemDB: CartItemDB)
    suspend fun clearCart()
    fun getProductCount(): Flow<List<CartItemCount>>
    suspend fun getRandomItemByCode(code:String):CartItemDB?
}