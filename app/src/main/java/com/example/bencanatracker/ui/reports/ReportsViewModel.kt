package com.example.bencanatracker.ui.reports

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bencanatracker.network.ApiClient
import com.example.bencanatracker.response.ReportsResponse
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {
    private val _reportsLiveData: MutableLiveData<ReportsResponse> = MutableLiveData()
    val reportsLiveData: LiveData<ReportsResponse> get() = _reportsLiveData

    fun fetchReportsData(selectedDisasterType: String? = null) {
        viewModelScope.launch {
            try {
                val apiService = ApiClient.apiService
                val response = if (!selectedDisasterType.isNullOrEmpty()) {
                    apiService.getReports(disasterType = selectedDisasterType)
                } else {
                    apiService.getReports()
                }

                // Print the received disasterType values
                for (geometry in response.result.objects.output.geometries) {
                    val disasterType = geometry.properties.disasterType
                }

                _reportsLiveData.postValue(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

}