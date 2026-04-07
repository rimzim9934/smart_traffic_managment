package com.example.smarttrafficapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smarttrafficapp.domain.model.Lane
import com.example.smarttrafficapp.domain.model.SignalState

class DashboardViewModel : ViewModel() {

    val lanes = MutableLiveData<List<Lane>>()
    val isSimulating = MutableLiveData(false)

    fun loadJunction(id: Int) {
        lanes.value = listOf(
            Lane(1, id, 1, 10, SignalState.GREEN, 20),
            Lane(2, id, 2, 5, SignalState.RED, 15),
            Lane(3, id, 3, 8, SignalState.YELLOW, 10),
            Lane(4, id, 4, 3, SignalState.RED, 25)
        )
    }

    fun startSimulation() {
        isSimulating.value = true
    }

    fun stopSimulation() {
        isSimulating.value = false
    }

    fun triggerEmergency(lane: Lane) {
        val currentLanes = lanes.value ?: return
        val updatedLanes = currentLanes.map {
            if (it.id == lane.id) {
                it.copy(isEmergency = true, signalState = SignalState.GREEN, timeRemaining = 99)
            } else {
                it.copy(signalState = SignalState.RED)
            }
        }
        lanes.value = updatedLanes
    }

    fun forceGreen(lane: Lane) {
        val currentLanes = lanes.value ?: return
        val updatedLanes = currentLanes.map {
            if (it.id == lane.id) {
                it.copy(signalState = SignalState.GREEN, timeRemaining = 30)
            } else {
                it.copy(signalState = SignalState.RED)
            }
        }
        lanes.value = updatedLanes
    }
}
