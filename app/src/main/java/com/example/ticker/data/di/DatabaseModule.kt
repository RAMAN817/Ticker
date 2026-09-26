package com.yourpackage.ticker.di

import android.content.Context
import androidx.room.Room
import com.example.ticker.data.local.StockQuoteDao
import com.example.ticker.data.local.TickerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTickerDatabase(@ApplicationContext context: Context): TickerDatabase {
        return Room.databaseBuilder(
            context,
            TickerDatabase::class.java,
            "ticker_db"
        ).build()
    }

    @Provides
    fun provideStockQuoteDao(database: TickerDatabase): StockQuoteDao {
        return database.stockQuoteDao()
    }
}