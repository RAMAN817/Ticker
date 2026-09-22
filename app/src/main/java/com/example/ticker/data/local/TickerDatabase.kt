package com.example.ticker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [StockQuoteEntity::class],
    version = 1,
    exportSchema = false
    )

abstract class TickerDatabase : RoomDatabase(){
    abstract fun stockQuoteDuo():  StockQuoteDao
}
