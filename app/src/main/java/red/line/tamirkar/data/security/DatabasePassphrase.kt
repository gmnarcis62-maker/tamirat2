package red.line.tamirkar.data.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom

object DatabasePassphrase {

    private const val PREFS_NAME = "encrypted_db_prefs"
    private const val PASSPHRASE_KEY = "db_passphrase"

    fun getOrCreate(context: Context): ByteArray {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val existing = prefs.getString(PASSPHRASE_KEY, null)
        return if (existing != null) {
            existing.toByteArray(Charsets.UTF_8)
        } else {
            val newPassphrase = generatePassphrase()
            prefs.edit().putString(PASSPHRASE_KEY, newPassphrase).apply()
            newPassphrase.toByteArray(Charsets.UTF_8)
        }
    }

    private fun generatePassphrase(): String {
        val random = SecureRandom()
        val bytes = ByteArray(32)
        random.nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
