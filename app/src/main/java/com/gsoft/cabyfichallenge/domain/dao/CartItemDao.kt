package com.gsoft.cabyfichallenge.domain.dao


import androidx.room.*
import com.gsoft.cabyfichallenge.domain.entity.CartItemCount
import com.gsoft.cabyfichallenge.domain.entity.CartItemDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {

    @Transaction
    suspend fun upsert(cartItemDB: CartItemDB) {
        if (getById(cartItemDB.id) != null) {
            update(cartItemDB)
        } else {
            insert(cartItemDB)
        }
    }

    @Transaction
    suspend fun upsert(list: List<CartItemDB>) {
        for (cartItemDB in list) {
            upsert(cartItemDB)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItemDB: CartItemDB)

    @Update
    fun update(cartItemDB: CartItemDB)

    @Query("SELECT * FROM CartItem WHERE Id = :id LIMIT 1")
    suspend fun getById(id: Int): CartItemDB?

    @Query("""SELECT * FROM CartItem""")
    suspend fun getCartItemList(): List<CartItemDB>

    @Delete
    suspend fun deleteCartItemDB(cartItemDB: CartItemDB)

    @Query("DELETE FROM CartItem")
    suspend fun deleteAllCartItems()

    @Query("""
        SELECT id, price, name, code, minQuantity, discountPercentaje,promoString, discountType, count(code) as 'quantity', price as 'originalPrice'
        FROM 'CartItem'
        GROUP by code
        """)
    fun getCartItemCount(): Flow<List<CartItemCount>>

    @Query("SELECT * FROM CartItem WHERE code = :code ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomItemByCode(code:String): CartItemDB?
}