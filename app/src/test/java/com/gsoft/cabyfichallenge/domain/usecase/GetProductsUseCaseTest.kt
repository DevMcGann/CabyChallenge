
package com.gsoft.cabyfichallenge.domain.usecase

import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import com.gsoft.cabyfichallenge.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetProductsUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: ProductRepository

     lateinit var useCase: GetProductsUseCase

     @Before
     fun onBefore(){
         MockKAnnotations.init(this)
         useCase = GetProductsUseCase(repository)
     }

    @Test
    fun `get products fro API success`() = runBlocking {
        //Given
        val product = Product(code = "VOUCHER", name = "any", price = 1.1)
        val myList = Resource.Success(ProductsResponse(listOf(product, product)))
        coEvery { repository.getFeedContent() } returns myList

        //When
        val sut = useCase()

        //Then
        coVerify(exactly = 1) { repository.getFeedContent() }
        assert(sut == myList)
    }

    @Test
    fun `get products fro API failure`() = runBlocking {
        //Given
        val exception = Resource.Failure(Exception())
        coEvery { repository.getFeedContent() } returns exception

        //When
        val sut = useCase()

        //Then
        coVerify(exactly = 1) { repository.getFeedContent() }
        assert(sut == exception)
    }






}

