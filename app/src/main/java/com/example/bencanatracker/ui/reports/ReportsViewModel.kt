import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bencanatracker.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportsViewModel : ViewModel() {
    private val _reportsLiveData: MutableLiveData<ReportsResponse> = MutableLiveData()
    val reportsLiveData: LiveData<ReportsResponse> get() = _reportsLiveData

    fun fetchReportsData() {
        viewModelScope.launch {
            try {
                val apiService = ApiService.create()
                val response = withContext(Dispatchers.IO) {
                    apiService.getReports()
                }
                _reportsLiveData.postValue(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
