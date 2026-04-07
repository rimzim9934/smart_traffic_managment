package com.example.smarttrafficapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "signal_logs")
data class SignalLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val junctionId: Int,
    val laneNumber: Int,
    val vehicleCount: Int,
    val signalState: String,
    val timestamp: Long = System.currentTimeMillis()
)