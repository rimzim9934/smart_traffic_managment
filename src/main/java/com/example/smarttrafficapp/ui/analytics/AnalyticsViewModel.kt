package com.example.smarttrafficapp.ui.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smarttrafficapp.domain.model.SignalLog

class AnalyticsViewModel : ViewModel() {
    // For now, returning empty list or dummy data
    fun getLogs(junctionId: Int): LiveData<List<SignalLog>> {
        return MutableLiveData(emptyList())
    }
}
