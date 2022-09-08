package com.ericg.kripto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ericg.kripto.data.local.entity.CoinEntity

@Dao
interface CoinDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Query("DELETE FROM coins")
    fun clearCoins()

    @Query("SELECT * FROM coins LIMIT 500")
    suspend fun getCoins(): List<CoinEntity>

    @Query("SELECT * FROM coins WHERE name LIKE '%' || :params || '%' OR CAST(rank as String) LIKE :params")
    suspend fun searchCoin(params: String): List<CoinEntity>
}
