package com.ericg.kripto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ericg.kripto.data.remote.dto.CoinDto

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins LIMIT 500")
    suspend fun getCoins(): List<CoinDto>

    @Query("SELECT * FROM coins WHERE name LIKE '%' || :params || '%' OR CAST(rank as String) LIKE '%' || :params || '%'")
    suspend fun searchCoin(params: String): List<CoinDto>

    @Insert(onConflict = REPLACE)
    suspend fun insertCoins(coins: List<CoinDto>)
}
