package com.gsoft.cabyfichallenge.data.repository

import androidx.lifecycle.LiveData
import com.gsoft.cabyfichallenge.data.datasource.remote.ProductApi
import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.ProductMapper
import com.gsoft.cabyfichallenge.domain.dao.CartItemDao
import com.gsoft.cabyfichallenge.domain.entity.CartItemCount
import com.gsoft.cabyfichallenge.domain.entity.CartItemDB
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import com.gsoft.cabyfichallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor (
    private val cartItemDao: CartItemDao,
    private val apiService: ProductApi,
        ): ProductRepository {

    override suspend fun getFeedContent(): Resource<ProductsResponse> {
        return try {
            val response = apiService.getProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addProductToCart(cartItemDB: CartItemDB) {
        cartItemDao.upsert(cartItemDB)
    }

    override suspend fun deleteProductFromCart(cartItemDB: CartItemDB) {
        cartItemDao.deleteCartItemDB(cartItemDB)
    }

    override suspend fun clearCart() {
        cartItemDao.deleteAllCartItems()
    }

    override fun getProductCount(): Flow<List<CartItemCount>> {
        return cartItemDao.getCartItemCount()
    }

    override suspend fun getRandomItemByCode(code: String): CartItemDB? {
        return cartItemDao.getRandomItemByCode(code)
    }


}