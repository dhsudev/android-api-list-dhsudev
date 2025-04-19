package com.example.apilist.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromImageUris(imageUris: ImageUris?): String {
        return Gson().toJson(imageUris)
    }

    @TypeConverter
    fun toImageUris(json: String): ImageUris? {
        return Gson().fromJson(json, ImageUris::class.java)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}