package com.gsoft.cabyfichallenge.data.datasource.local

import com.gsoft.cabyfichallenge.domain.entity.CartItemCount

/* something like this should be handled by front
* Maybe the best workaround for this could be to send the API the current cart
* and let them handle the price adjustments and return the object
* just doing this for the sake of this demo app only
*
* */

fun CalculatePrice(list:List<CartItemCount>) : List<CartItemCount> {

    for (product in list){
        if(product.discountType != null && product.discountPercentaje != null && product.minQuantity != null){
            when(product.discountType){
                "BULK" -> {
                    product.originalPrice = product.price
                    if (product.quantity >= product.minQuantity){
                        product.price = (product.price - (product.price * product.discountPercentaje)) * product.quantity
                    }
                }
                "50%" ->{
                    product.originalPrice = product.price
                    if(product.quantity >= product.minQuantity){
                        val unitPrice = product.price
                        if(product.quantity % 2 != 0){
                            product.price = ((product.price  * (product.quantity -1) ) / 2) + unitPrice
                        }else{
                            product.price = (product.price /2) * product.quantity
                        }
                    }
                }
            }
        }
    }

    return list
}

