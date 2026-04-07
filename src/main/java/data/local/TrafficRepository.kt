package com.example.smarttrafficapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.smarttrafficapp.data.local.TrafficDatabase
import com.example.smarttrafficapp.domain.model.*

class TrafficRepository(context: Context) {

    private val db = TrafficDatabase.getInstance(context)
    private val junctionDao = db.junctionDao()
    private val laneDao = db.laneDao()
    private val logDao = db.signalLogDao()

    fun getAllJunctions(): LiveData<List<Junction>> = junctionDao.getAllJunctions()

    suspend fun getJunctionById(id: Int): Junction? = junctionDao.getJunctionById(id)

    suspend fun insertJunction(junction: Junction) = junctionDao.insertJunction(junction)

    suspend fun seedDemoData() {
        if (junctionDao.getCount() > 0) return

        val junctions = listOf(
            Junction(1, "MG Road Junction",      23.2599, 77.4126),
            Junction(2, "DB Mall Crossing",       23.2347, 77.4341),
            Junction(3, "Hoshangabad Road Cross", 23.2156, 77.4340),
            Junction(4, "Arera Colony Signal",    23.2189, 77.4526),
            Junction(5, "Board Office Chowk",     23.2650, 77.4068)
        )
        junctionDao.insertAll(junctions)

        val lanes = mutableListOf<Lane>()
        junctions.forEach { j ->
            for (i in 1..4) {
                lanes.add(
                    Lane(
                        junctionId = j.id,
                        laneNumber = i,
                        vehicleCount = (5..40).random(),
                        signalState = if (i == 1) SignalState.GREEN else SignalState.RED,
                        timeRemaining = if (i == 1) 30 else 45
                    )
                )
            }
        }
        laneDao.insertAll(lanes)
    }

    fun getLanesForJunction(junctionId: Int): LiveData<List<Lane>> =
        laneDao.getLanesForJunction(junctionId)

    suspend fun getLanesOnce(junctionId: Int): List<Lane> =
        laneDao.getLanesForJunctionOnce(junctionId)

    suspend fun updateLane(lane: Lane) = laneDao.updateLane(lane)

    fun getLogsForJunction(junctionId: Int) = logDao.getLogsForJunction(junctionId)

    suspend fun insertLog(log: SignalLog) = logDao.insertLog(log)
}