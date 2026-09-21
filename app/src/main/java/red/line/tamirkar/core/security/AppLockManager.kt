package red.line.tamirkar.core.security

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.appLockDataStore by preferencesDataStore(name = "app_lock_prefs")

@Singleton
class AppLockManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_LOCK_ENABLED = booleanPreferencesKey("lock_enabled")
        private val KEY_BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        private val KEY_PIN_SET = booleanPreferencesKey("pin_set")
    }

    val isLockEnabled: Flow<Boolean> = context.appLockDataStore.data
        .map { it[KEY_LOCK_ENABLED] ?: false }

    val isBiometricEnabled: Flow<Boolean> = context.appLockDataStore.data
        .map { it[KEY_BIOMETRIC_ENABLED] ?: false }

    suspend fun setLockEnabled(enabled: Boolean) {
        context.appLockDataStore.edit { it[KEY_LOCK_ENABLED] = enabled }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.appLockDataStore.edit { it[KEY_BIOMETRIC_ENABLED] = enabled }
    }

    suspend fun setPinSet(set: Boolean) {
        context.appLockDataStore.edit { it[KEY_PIN_SET] = set }
    }
}