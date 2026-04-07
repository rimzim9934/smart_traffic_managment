package com.example.smarttrafficapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smarttrafficapp.domain.model.SignalLog

@Dao
interface SignalLogDao {
    @Query("SELECT * FROM signal_logs WHERE junctionId = :junctionId ORDER BY timestamp DESC LIMIT 50")
    fun getLogsForJunction(junctionId: Int): LiveData<List<SignalLog>>

    @Insert
    suspend fun insertLog(log: SignalLog)

    @Query("DELETE FROM signal_logs WHERE timestamp < :cutoff")
    suspend fun deleteOldLogs(cutoff: Long)
}