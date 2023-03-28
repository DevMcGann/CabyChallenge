package com.gsoft.cabyfichallenge.domain.usecase

import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import com.gsoft.cabyfichallenge.util.Resource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class GetProductsUseCaseTest {
    private lateinit var useCase: GetProductsUseCase
    private val repository: ProductRepository = mock(ProductRepository::class.java)
    private val prod: Product = mock(Product::class.java)

    @BeforeEach
    fun setUp() {
        useCase = GetProductsUseCase(repository)
    }

    @Test
    suspend fun `test invoke() returns Success`() {
        val response = Resource.Success(ProductsResponse(products = listOf(prod)))
        `when`(repository.getFeedContent()).thenReturn(response)
        try {
            val result = useCase.invoke()
            Assertions.assertEquals(result, response)
        }catch (e: Exception){
            Assertions.fail("Error occurred: ${e.message}")
        }

    }
}
