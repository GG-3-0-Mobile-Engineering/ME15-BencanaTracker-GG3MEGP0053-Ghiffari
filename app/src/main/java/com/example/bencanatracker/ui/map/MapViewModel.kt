package com.example.bencanatracker.ui.map

import AreaList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bencanatracker.network.ApiClient
import com.example.bencanatracker.response.Geometry
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _reportsList = MutableLiveData<List<Geometry>>()
    val reportsList: LiveData<List<Geometry>> get() = _reportsList

    private var allReports: List<Geometry>? = null

    fun fetchReportsAndAddMarkers(selectedProvince: String? = null, selectedDisasterType: String? = null) {
        viewModelScope.launch {
            try {
                val apiService = ApiClient.apiService

                // Call the API using the filter parameters (selectedDisasterType) if provided
                val reportsResponse = if (!selectedDisasterType.isNullOrEmpty()) {
                    apiService.getReports(disasterType = selectedDisasterType)
                } else {
                    apiService.getReports()
                }

                // Update allReports with the retrieved reports data
                allReports = reportsResponse.result.objects.output.geometries

                // If selectedProvince is provided, filter the reports based on it
                val filteredReports = if (!selectedProvince.isNullOrEmpty()) {
                    allReports?.filter { geometry ->
                        val instanceRegionCode = geometry.properties.tags.instanceRegionCode ?: ""
                        val provinceName = AreaList().ProvinceCode[instanceRegionCode] ?: ""
                        provinceName.equals(selectedProvince, ignoreCase = true)
                    }
                } else {
                    allReports
                }

                _reportsList.value = filteredReports

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}