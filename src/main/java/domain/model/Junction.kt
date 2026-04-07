package com.example.smarttrafficapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "junctions")
data class Junction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val laneCount: Int = 4
)