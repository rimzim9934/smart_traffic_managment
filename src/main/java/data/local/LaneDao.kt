package com.example.smarttrafficapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smarttrafficapp.domain.model.Lane
import com.example.smarttrafficapp.domain.model.SignalState

@Dao
interface LaneDao {
    @Query("SELECT * FROM lanes WHERE junctionId = :junctionId ORDER BY laneNumber")
    fun getLanesForJunction(junctionId: Int): LiveData<List<Lane>>

    @Query("SELECT * FROM lanes WHERE junctionId = :junctionId ORDER BY laneNumber")
    suspend fun getLanesForJunctionOnce(junctionId: Int): List<Lane>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLane(lane: Lane)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lanes: List<Lane>)

    @Update
    suspend fun updateLane(lane: Lane)

    @Query("UPDATE lanes SET signalState = :state, timeRemaining = :time WHERE id = :laneId")
    suspend fun updateSignal(laneId: Int, state: SignalState, time: Int)

    @Query("UPDATE lanes SET vehicleCount = :count WHERE id = :laneId")
    suspend fun updateVehicleCount(laneId: Int, count: Int)
}