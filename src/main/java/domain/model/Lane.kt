package com.example.smarttrafficapp.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "lanes",
    foreignKeys = [ForeignKey(
        entity = Junction::class,
        parentColumns = ["id"],
        childColumns = ["junctionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Lane(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val junctionId: Int,
    val laneNumber: Int,
    val vehicleCount: Int = 0,
    val signalState: SignalState = SignalState.RED,
    val timeRemaining: Int = 30,
    val congestionLevel: CongestionLevel = CongestionLevel.LOW,
    val isEmergency: Boolean = false
)

enum class CongestionLevel {
    LOW, MEDIUM, HIGH
}