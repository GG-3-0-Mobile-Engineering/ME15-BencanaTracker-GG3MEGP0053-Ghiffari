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


    // Function to set the search query
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Function to set the selected province
    fun setSelectedProvince(province: String) {
        _selectedProvince.value = province
    }

}