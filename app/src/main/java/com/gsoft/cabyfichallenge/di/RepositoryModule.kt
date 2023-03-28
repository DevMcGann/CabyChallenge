package com.gsoft.cabyfichallenge.di

import com.gsoft.cabyfichallenge.data.datasource.remote.ProductApi
import com.gsoft.cabyfichallenge.data.repository.CheckoutRespositoryImpl
import com.gsoft.cabyfichallenge.data.repository.ProductRepositoryImpl
import com.gsoft.cabyfichallenge.domain.dao.CartItemDao
import com.gsoft.cabyfichallenge.domain.repository.CheckoutRepository
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import com.gsoft.cabyfichallenge.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(
        cartItemDao: CartItemDao,
        apiService: ProductApi,

        ): ProductRepository {
        return ProductRepositoryImpl(
            cartItemDao = cartItemDao,
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun providesCheckoutRepository(
        apiService: ProductApi,
        ): CheckoutRepository {
        return CheckoutRespositoryImpl(
            apiService = apiService
        )
    }



    @Provides
    fun provideUseCases(
        repo: ProductRepository,
        checkoutRepository: CheckoutRepository
    ) = Usecases(
        getProductsUseCase = GetProductsUseCase(repo),
        saveProductsAsCartItemUseCase = SaveProductsAsCartItemUseCase(repo),
        getCartItemsCountUseCase = GetCartItemsCountUseCase(repo),
        deleteCartItemUseCase = DeleteCartItemUseCase(repo),
        wipeCartItemUseCase = WipeCartItemUseCase(repo),
        checkoutUseCase = CheckoutUseCase(checkoutRepository)
    )


}