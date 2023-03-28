package com.gsoft.cabyfichallenge.domain.usecase

data class Usecases(
    val getProductsUseCase: GetProductsUseCase,
    val saveProductsAsCartItemUseCase: SaveProductsAsCartItemUseCase,
    val getCartItemsCountUseCase: GetCartItemsCountUseCase,
    val deleteCartItemUseCase: DeleteCartItemUseCase,
    val wipeCartItemUseCase: WipeCartItemUseCase,
    val checkoutUseCase: CheckoutUseCase

)