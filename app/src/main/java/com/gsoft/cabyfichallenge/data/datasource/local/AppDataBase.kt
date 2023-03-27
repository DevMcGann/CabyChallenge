package com.gsoft.cabyfichallenge.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gsoft.cabyfichallenge.domain.dao.CartItemDao
import com.gsoft.cabyfichallenge.domain.dao.typeConverter
import com.gsoft.cabyfichallenge.domain.entity.CartItemDB

@TypeConverters(typeConverter::class)
@Database(entities = [CartItemDB::class],
    version = 4, exportSchema = false)

abstract class AppDataBase: RoomDatabase() {
    abstract fun cartitemDao(): CartItemDao

}