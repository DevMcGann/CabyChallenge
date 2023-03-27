package com.gsoft.cabyfichallenge.di

import android.app.Application
import androidx.room.Room
import com.gsoft.cabyfichallenge.data.datasource.local.AppDataBase
import com.gsoft.cabyfichallenge.domain.dao.CartItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, "CabyChallenge")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCartItemDao(appDataBase: AppDataBase): CartItemDao {
        return appDataBase.cartitemDao()
    }


}