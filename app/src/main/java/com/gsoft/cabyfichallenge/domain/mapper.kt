package com.gsoft.cabyfichallenge.domain

import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.addFields
import com.gsoft.cabyfichallenge.domain.entity.CartItemDB

object ProductMapper {
    fun fromDb(cartItem: CartItemDB?) =
        cartItem?.let {
            Product(
                code = it.code,
                name = it.name,
                price = it.price
            )
        }

    fun toDb(product: Product) : CartItemDB{
        val newProduct : Product = product.addFields()
        return CartItemDB(
            code = newProduct.code,
            name = newProduct.name,
            price = newProduct.price,
            discountType = newProduct.discountType,
            discountPercentaje = newProduct.discountPercentaje,
            minQuantity = newProduct.minQuantity,
            promoString = newProduct.promoString
        )
    }


    fun listFromDb(list: List<CartItemDB>) =
        list.map { fromDb(it) }

    fun listToDb(list: List<Product>) =
        list.map { toDb(it) }

    fun oneToDb(product: Product) : CartItemDB{
        return toDb(product)
    }

    fun oneFromDb(cartItem: CartItemDB){
        fromDb(cartItem)
    }
}