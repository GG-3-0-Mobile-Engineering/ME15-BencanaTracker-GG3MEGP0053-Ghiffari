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
    private var selectedDisasterType: String? = null
    private var selectedProvince: String? = null

    fun fetchReportsAndAddMarkers(
        selectedProvince: String? = null,
        selectedDisasterType: String? = null,
        applyFilter: Boolean = false
    ) {
        this.selectedDisasterType = selectedDisasterType
        this.selectedProvince = selectedProvince

        viewModelScope.launch {
            try {
                val apiService = ApiClient.apiService

                val reportsResponse = if (!selectedDisasterType.isNullOrEmpty()) {
                    apiService.getReports(disasterType = selectedDisasterType)
                } else {
                    apiService.getReports()
                }

                allReports = reportsResponse.result.objects.output.geometries

                val filteredReports = if (applyFilter) {
                    allReports?.filter { geometry ->
                        val instanceRegionCode = geometry.properties.tags.instanceRegionCode ?: ""
                        val provinceName = AreaList().ProvinceCode[instanceRegionCode] ?: ""
                        val reportType = geometry.properties.disasterType ?: ""

                        val provinceFilterMatch = selectedProvince.isNullOrEmpty() || provinceName.equals(selectedProvince, ignoreCase = true)
                        val disasterTypeFilterMatch = selectedDisasterType.isNullOrEmpty() || reportType.equals(selectedDisasterType, ignoreCase = true)

                        provinceFilterMatch && disasterTypeFilterMatch
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

    // ... Other methods ...
}