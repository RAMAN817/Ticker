package com.example.ticker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
//flow emits a new list
//:category is just a placeholder for kotlin parameter
interface StockQuoteDao{
    @Query("SELECT * FROM stock_quotes WHERE category = :category ORDER BY symbol ASC")
    fun observerByCategory(category: String): Flow<List<StockQuoteEntity>>

    @Query("SELECT * FROM stock_quotes WHERE category = :category ORDER BY symbol ASC")
    suspend fun getByCategory(category: String): List<StockQuoteEntity>

    @Query("SELECT MIN(lastUpdated) FROM stock_quotes WHERE category = :category ")
    suspend fun getOldestTimeStamp(category: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(quotes: List<StockQuoteEntity>)

    @Query("DELETE FROM stock_quotes WHERE category = :category")
    suspend fun clearCategory(category: String)




}


