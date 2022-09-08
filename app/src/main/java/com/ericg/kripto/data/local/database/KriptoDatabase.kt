package com.ericg.kripto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ericg.kripto.data.local.dao.CoinDao
import com.ericg.kripto.data.local.dao.ExchangeDao
import com.ericg.kripto.data.local.entity.CoinEntity
import com.ericg.kripto.data.local.entity.ExchangeEntity
import com.ericg.kripto.data.local.typeconverter.DataTypeConverters

@Database(
    entities = [CoinEntity::class, ExchangeEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DataTypeConverters::class)
abstract class KriptoDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
    abstract val exchangeDao: ExchangeDao
}
