package red.line.tamirkar.core.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_lock_prefs")

class AppLockManager(private val context: Context) {

    companion object {
        val IS_LOCKED = booleanPreferencesKey("is_locked")
        val LAST_ACTIVE_TIME = longPreferencesKey("last_active_time")
        val LOCK_TIMEOUT = longPreferencesKey("lock_timeout")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        const val DEFAULT_TIMEOUT_MS = 5 * 60 * 1000L
    }

    val isLocked: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOCKED] ?: false
    }

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[BIOMETRIC_ENABLED] ?: false
    }

    suspend fun setLocked(locked: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOCKED] = locked
        }
    }

    suspend fun updateLastActiveTime() {
        context.dataStore.edit { prefs ->
            prefs[LAST_ACTIVE_TIME] = System.currentTimeMillis()
        }
    }

    suspend fun shouldLock(): Boolean {
        val prefs = context.dataStore.data.map { it }
        val lastActive = prefs.map { it[LAST_ACTIVE_TIME] ?: 0L }
        val timeout = prefs.map { it[LOCK_TIMEOUT] ?: DEFAULT_TIMEOUT_MS }
        val elapsed = System.currentTimeMillis() - (lastActive.first())
        return elapsed > timeout.first()
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[BIOMETRIC_ENABLED] = enabled
        }
    }

    suspend fun setLockTimeout(timeoutMs: Long) {
        context.dataStore.edit { prefs ->
            prefs[LOCK_TIMEOUT] = timeoutMs
        }
    }
}
