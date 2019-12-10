package com.luisansal.jetpack.domain.entity

import com.google.android.gms.maps.model.LatLng
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "tblvisit", foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = CASCADE)])
class Visit(var location: LatLng, var userId: Long?) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
