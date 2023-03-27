package com.gsoft.cabyfichallenge.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CartItem")
data class CartItemDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val code: String,
    var price: Double,
    val discountType:String? = null,
    val discountPercentaje : Double? = null,
    val minQuantity : Int? = null
)



