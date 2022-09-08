package com.ericg.kripto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericg.kripto.data.local.dao.CoinDao
import com.ericg.kripto.data.local.entity.CoinEntity
import com.ericg.kripto.data.local.entity.ExchangeEntity

@Database(
    entities = [CoinEntity::class, ExchangeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class KriptoDatabase: RoomDatabase() {
    abstract val coinDao : CoinDao
}
