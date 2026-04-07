package com.example.smarttrafficapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smarttrafficapp.domain.model.Junction

class MapViewModel : ViewModel() {

    private val _junctions = MutableLiveData<List<Junction>>()
    val junctions: LiveData<List<Junction>> = _junctions

    fun seedData() {
        _junctions.value = listOf(
            Junction(1, "Junction 1", 23.2599, 77.4126),
            Junction(2, "Junction 2", 23.2700, 77.4200)
        )
    }
}
