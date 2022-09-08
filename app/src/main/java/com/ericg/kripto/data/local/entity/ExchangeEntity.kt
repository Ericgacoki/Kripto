package com.ericg.kripto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ericg.kripto.data.local.typeconverter.DataTypeConverters

@Entity(tableName = "exchanges")
data class ExchangeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    val adjustedRank: Int,
    val reportedRank: Int,
    val markets: Int,
    val currencies: Int,
    @TypeConverters(DataTypeConverters::class)
    val twitterLinks: List<String>?,
    @TypeConverters(DataTypeConverters::class)
    val websiteLinks: List<String>?,
)
