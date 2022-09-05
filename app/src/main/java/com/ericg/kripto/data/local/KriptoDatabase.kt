package com.ericg.kripto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericg.kripto.data.remote.dto.CoinDto

@Database(
    entities = [CoinDto::class],
    version = 1,
    exportSchema = false
)
abstract class KriptoDatabase: RoomDatabase() {
    abstract val coinDao : CoinDao
}
