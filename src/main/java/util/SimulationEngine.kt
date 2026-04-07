package com.example.smarttrafficapp.util

import com.example.smarttrafficapp.domain.model.Lane
import com.example.smarttrafficapp.domain.model.SignalState
import com.example.smarttrafficapp.domain.model.SignalLog
import com.example.smarttrafficapp.data.repository.TrafficRepository
import kotlinx.coroutines.*

class SimulationEngine(
    private val repository: TrafficRepository,
    private val junctionId: Int,
    private val scope: CoroutineScope,
    private val onUpdate: (List<Lane>) -> Unit
) {

    private var job: Job? = null
    private var activeLaneIndex = 0

    private val greenDuration = 30
    private val yellowDuration = 5
    private val redDuration = 45

    fun start() {
        job = scope.launch {
            while (isActive) {
                cycleLanes()
            }
        }
    }

    fun stop() {
        job?.cancel()
    }

    private suspend fun cycleLanes() {
        val lanes = repository.getLanesOnce(junctionId)
        if (lanes.isEmpty()) return

        val updated = lanes.mapIndexed { index, lane ->
            lane.copy(
                signalState = if (index == activeLaneIndex) SignalState.GREEN else SignalState.RED,
                timeRemaining = if (index == activeLaneIndex) greenDuration else redDuration,
                vehicleCount = (5..50).random()
            )
        }

        updated.forEach { repository.updateLane(it) }
        onUpdate(updated)

        // countdown green
        for (t in greenDuration downTo 1) {
            delay(1000)
            val temp = updated.toMutableList()
            temp[activeLaneIndex] = temp[activeLaneIndex].copy(timeRemaining = t)
            onUpdate(temp)
        }

        // yellow
        val yellow = updated.toMutableList()
        yellow[activeLaneIndex] = yellow[activeLaneIndex].copy(
            signalState = SignalState.YELLOW,
            timeRemaining = yellowDuration
        )
        onUpdate(yellow)

        repository.insertLog(
            SignalLog(
                junctionId = junctionId,
                laneNumber = activeLaneIndex + 1,
                vehicleCount = updated[activeLaneIndex].vehicleCount,
                signalState = SignalState.GREEN.name
            )
        )

        delay(yellowDuration * 1000L)

        activeLaneIndex = (activeLaneIndex + 1) % lanes.size
    }
}
