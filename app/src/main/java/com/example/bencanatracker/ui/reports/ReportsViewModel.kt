package com.example.bencanatracker.ui.reports

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

    fun fetchReportsData() {
        viewModelScope.launch {
            try {
                val apiService = ApiClient.apiService
                val response = apiService.getReports()
                _reportsLiveData.postValue(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}