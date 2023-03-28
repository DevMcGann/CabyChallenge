package com.gsoft.cabyfichallenge.domain.usecase

import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import com.gsoft.cabyfichallenge.util.Resource
import javax.inject.Inject

class GetProductsUseCase  @Inject constructor(
    private val productRepository: ProductRepository) {
    suspend operator fun invoke() : Resource<ProductsResponse> {
        return  productRepository.getFeedContent()
    }
}