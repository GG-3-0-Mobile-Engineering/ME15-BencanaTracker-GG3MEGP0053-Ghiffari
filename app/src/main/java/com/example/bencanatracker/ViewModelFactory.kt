import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bencanatracker.SettingPreferences
import com.example.bencanatracker.ui.setting.SettingViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val pref: SettingPreferences,
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
