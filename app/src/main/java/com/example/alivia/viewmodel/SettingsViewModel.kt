import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _isNotificationsEnabled = MutableStateFlow(false)
    val isNotificationsEnabled: StateFlow<Boolean> get() = _isNotificationsEnabled

    private val _areAnimationsEnabled = MutableStateFlow(true) // Estado inicial para animações
    val areAnimationsEnabled: StateFlow<Boolean> get() = _areAnimationsEnabled

    fun setNotificationsEnabled(enabled: Boolean) {
        _isNotificationsEnabled.value = enabled
    }

    fun setAnimationsEnabled(enabled: Boolean) {
        _areAnimationsEnabled.value = enabled
    }
}
