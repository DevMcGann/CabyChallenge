package com.gsoft.cabyfichallenge.domain.useCase


import com.gsoft.cabyfichallenge.domain.entity.CartItemCount
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsCountUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
     fun invoke() : Flow<List<CartItemCount>> {
        return  productRepository.getProductCount()
    }
}