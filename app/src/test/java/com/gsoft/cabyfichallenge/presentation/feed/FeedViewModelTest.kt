package com.gsoft.cabyfichallenge.presentation.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gsoft.cabyfichallenge.MainCoroutineRule
import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.usecase.GetProductsUseCase
import com.gsoft.cabyfichallenge.domain.usecase.SaveProductsAsCartItemUseCase
import com.gsoft.cabyfichallenge.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class FeedViewModelTest{
    @RelaxedMockK
    private lateinit var getProductsUseCase: GetProductsUseCase

    @RelaxedMockK
    private lateinit var saveProductsAsCartItemUseCase: SaveProductsAsCartItemUseCase

    private lateinit var feedViewModel: FeedViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        feedViewModel = FeedViewModel(getProductsUseCase, saveProductsAsCartItemUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when useCase return a list of products set on the livedata`() = runTest {
        //Given
        val product = Product(code = "VOUCHER", name = "any", price = 1.1)
        val myList = Resource.Success(ProductsResponse(listOf(product, product)))

        coEvery { getProductsUseCase() } returns myList

        //When
        feedViewModel.fetchProducts()

        //Then
        advanceUntilIdle()
        assert(feedViewModel.products.value == myList)
    }
}