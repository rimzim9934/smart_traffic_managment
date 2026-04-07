package com.example.smarttrafficapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smarttrafficapp.domain.model.Junction

@Dao
interface JunctionDao {

    @Query("SELECT * FROM junctions")
    fun getAllJunctions(): LiveData<List<Junction>>

    @Query("SELECT * FROM junctions WHERE id = :id")
    suspend fun getJunctionById(id: Int): Junction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJunction(junction: Junction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(junctions: List<Junction>)

    @Query("SELECT COUNT(*) FROM junctions")
    suspend fun getCount(): Int
}
