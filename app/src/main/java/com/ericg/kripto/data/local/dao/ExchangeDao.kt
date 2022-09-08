package com.ericg.kripto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericg.kripto.data.local.entity.ExchangeEntity

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchanges(exchanges: List<ExchangeEntity>)

    @Query("DELETE FROM exchanges")
    fun clearExchanges()

    @Query("SELECT * FROM exchanges LIMIT 250")
    suspend fun getExchange(): List<ExchangeEntity>

    @Query("SELECT * FROM exchanges WHERE name LIKE '%' || :params || '%' OR CAST(adjustedRank as String) LIKE :params OR CAST(reportedRank as String) LIKE :params OR CAST(markets as String) LIKE :params OR CAST(currencies as String) LIKE :params")
    suspend fun searchExchange(params: String): List<ExchangeEntity>
}
