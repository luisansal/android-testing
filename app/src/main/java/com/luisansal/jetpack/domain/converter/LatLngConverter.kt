package com.luisansal.jetpack.domain.converter

import com.google.android.gms.maps.model.LatLng

import androidx.room.TypeConverter
import java.lang.Double


class LatLngConverter {

    @TypeConverter
    fun convertToEntityProperty(databaseValue: String?): LatLng? {
        if (databaseValue == null) {
            return null
        }
        val latLng = databaseValue.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        return LatLng(Double.valueOf(latLng[0]), Double.valueOf(latLng[1]))
    }

    @TypeConverter
    fun convertToDatabaseValue(entityProperty: LatLng?): String? {
        return if (entityProperty == null) null else entityProperty.latitude.toString() + ";" + entityProperty.longitude
    }
}
