package com.ericg.kripto.data.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataTypeConverters {
    @TypeConverter
    fun fromStringList(links: List<String?>?): String? {
        if (links == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(links, type)
    }

    @TypeConverter
    fun toStringList(linksString: String?): List<String>? {
        if (linksString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String>>(linksString, type)
    }
}
