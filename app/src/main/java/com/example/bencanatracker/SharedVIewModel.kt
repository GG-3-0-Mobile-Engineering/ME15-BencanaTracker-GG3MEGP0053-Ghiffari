package com.example.bencanatracker.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // MutableLiveData to store the search query
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    // MutableLiveData to store the selected province
    private val _selectedProvince = MutableLiveData<String>()
    val selectedProvince: LiveData<String> get() = _selectedProvince

    // MutableLiveData to store the selected disaster type
    private val _selectedDisasterType = MutableLiveData<String>()
    val selectedDisasterType: LiveData<String> get() = _selectedDisasterType

    // Function to set the search query
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Function to set the selected province
    fun setSelectedProvince(province: String) {
        _selectedProvince.value = province
    }

    // Function to set the selected disaster type
    fun setSelectedDisasterType(disasterType: String) {
        _selectedDisasterType.value = disasterType
    }
}
