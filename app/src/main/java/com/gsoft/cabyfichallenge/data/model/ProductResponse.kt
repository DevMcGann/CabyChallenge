package com.gsoft.cabyfichallenge.data.model



data class ProductsResponse(val products: List<Product>)

data class Product(
    val code: String,
    val name: String,
    val price: Double,
    var discountType : String? = null,
    var discountPercentaje : Double? = null,
    var minQuantity:Int? = null,
    var promoString : String? = null
)

//Ok... This is kinda ugly yes,  but i think that this kind of
// stuff should be managed by the backend and products should already come with this kind
//of data on them,  to be even showing some kind of offer UI in the main feed
fun Product.addFields() : Product{
    when (code){
            "VOUCHER" -> {
                discountType = "50%"
                discountPercentaje = 0.5
                minQuantity = 2
                promoString = "50% discount every $minQuantity units"
            }
           "TSHIRT" -> {
               discountType = "BULK"
               discountPercentaje = 0.05
               minQuantity = 3
               promoString = "-1$ discount on more than $minQuantity units"
           }
        else -> {
            discountType = null
            discountPercentaje = null
            minQuantity = null
        }

    }
    return this
}



