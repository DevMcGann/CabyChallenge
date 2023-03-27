package com.gsoft.cabyfichallenge.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CartItemCount")
data class CartItemCount(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val code: String,
    var originalPrice: Double? = null,
    var price: Double,
    val quantity: Int = 0,
    val discountType:String? = null,
    val discountPercentaje : Double? = null,
    val promoString : String? = null,
    val minQuantity : Int? = null
)