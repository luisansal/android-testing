package com.luisansal.jetpack.domain.entity

import com.google.android.gms.maps.model.LatLng
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblvisit")
data class Visit(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val location: LatLng
)