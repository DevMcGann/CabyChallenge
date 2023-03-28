package com.gsoft.cabyfichallenge.presentation.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.useCase.GetProductsUseCase
import com.gsoft.cabyfichallenge.domain.useCase.SaveProductsAsCartItemUseCase
import com.gsoft.cabyfichallenge.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FeedViewModelTest{


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FeedViewModel
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var saveProductsAsCartItemUseCase: SaveProductsAsCartItemUseCase

    @Before
    fun setUp() {
        getProductsUseCase = mock(GetProductsUseCase::class.java)
        saveProductsAsCartItemUseCase = mock(SaveProductsAsCartItemUseCase::class.java)
        viewModel = FeedViewModel(getProductsUseCase, saveProductsAsCartItemUseCase)
    }

    val productOne = Product("VOUCHER", name = "any", price = 1.0 )
    val productTwo = Product("TSHIRT", name = "any", price = 6.0 )

    @Test
    suspend fun `fetchData returns expected result`(){

        val list = listOf(productOne, productTwo)
        val response = Resource.Success (ProductsResponse(list))

        val liveData = MutableLiveData<Resource<ProductsResponse>>()
        liveData.value = response

        `when`(getProductsUseCase.invoke()).thenReturn(response)

        val result = viewModel.fetchProducts()

        assertEquals(response, result)
    }

}

