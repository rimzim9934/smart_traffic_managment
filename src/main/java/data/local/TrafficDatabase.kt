package com.example.smarttrafficapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smarttrafficapp.domain.model.Junction
import com.example.smarttrafficapp.domain.model.Lane
import com.example.smarttrafficapp.domain.model.SignalLog

@Database(
    entities = [Junction::class, Lane::class, SignalLog::class],
    version = 1,
    exportSchema = false
)
abstract class TrafficDatabase : RoomDatabase() {
    abstract fun junctionDao(): JunctionDao
    abstract fun laneDao(): LaneDao
    abstract fun signalLogDao(): SignalLogDao

    companion object {
        @Volatile private var INSTANCE: TrafficDatabase? = null

        fun getInstance(context: Context): TrafficDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TrafficDatabase::class.java,
                    "traffic_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}